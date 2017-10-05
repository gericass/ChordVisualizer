package db

data class Node(var id: Int, var name: String, var ip: String,
                var hash: String, var successor: String, var predecessor: String)
