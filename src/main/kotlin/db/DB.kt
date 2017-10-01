package db

import org.jetbrains.exposed.sql.*

class DB {


    init {
        Database.connect("jdbc:mysql://127.0.0.1:13306/chord_dht_repo", "com.mysql.jdbc.Driver","root","mysql")
    }

}