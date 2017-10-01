package main

import db.*

fun main(args: Array<String>) {
    val db = DB()
    val allNodes = db.getAllNodes()
    for(n in allNodes){
        println(n.id)
    }
}