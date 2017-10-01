package db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

data class Node(var id: Int,var name: String, var ip: String, var hash:String, var successor: String, var predecessor: String)

class DB {

    object chord_dht : Table() {
        val id = integer("id")
        val name = varchar("name",256)
        val ip = varchar("ip",256)
        val hash = varchar("hash",256)
        val successor = varchar("successor",256)
        val predecessor = varchar("predecessor",256)
    }

    fun getAllNodes(): MutableList<Node>{
        val nodes = mutableListOf<Node>()
        transaction {
            (chord_dht.selectAll()).forEach {
                val node = Node(it[chord_dht.id],it[chord_dht.name],it[chord_dht.ip],it[chord_dht.hash],it[chord_dht.successor],it[chord_dht.predecessor])
                nodes.add(node)
            }
        }

        return nodes
    }

    init {
        Database.connect("jdbc:mysql://127.0.0.1:13306/chord_dht_repo", "com.mysql.jdbc.Driver","root","mysql")
    }

}