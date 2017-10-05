package db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DB {

    object chord_dht : Table() {
        val id = integer("id")
        val name = varchar("name", 256)
        val ip = varchar("ip", 256)
        val hash = varchar("hash", 256)
        val successor = varchar("successor", 256)
        val predecessor = varchar("predecessor", 256)
    }

    var nodes = mutableListOf<Node>()

    fun getAllNodes(): MutableList<Node> {
        val nodes = mutableListOf<Node>()
        transaction {
            (chord_dht.selectAll()).forEach {
                val node = Node(
                        it[chord_dht.id],
                        it[chord_dht.name],
                        it[chord_dht.ip],
                        it[chord_dht.hash],
                        it[chord_dht.successor],
                        it[chord_dht.predecessor])
                nodes.add(node)
            }
        }
        this.nodes = nodes

        return nodes
    }

    fun getNodeByHash(hash: String): Node {
        var node: Node? = null
        /*
        transaction {
            (chord_dht.select { chord_dht.hash.eq(hash) }).forEach {
                node = Node(
                        it[chord_dht.id],
                        it[chord_dht.name],
                        it[chord_dht.ip],
                        it[chord_dht.hash],
                        it[chord_dht.successor],
                        it[chord_dht.predecessor])
            }
        }
        */


        for (i in nodes) {
            if (i.hash == hash) {
                node = i
            }
        }

        return node as Node
    }

    init {
        Database.connect("jdbc:mysql://127.0.0.1:13306/chord_dht_repo?useSSL=false", "com.mysql.jdbc.Driver", "root", "mysql")
    }

}