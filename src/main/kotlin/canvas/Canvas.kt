package canvas


import java.*
import processing.core.PApplet
import db.DB

class Canvas() : PApplet () {

    var conn: DB

    fun getHashList(): List<String>{
        val hashes = mutableListOf<String>()
        val nodeList = conn.getAllNodes()
        for(i in nodeList) {
            hashes.add(i.hash)
        }

        return hashes.sorted()
    }

    override fun settings() {
        size(500, 400)
    }
    override fun setup() {
        background(256.0f,256.0f,256.0f)
    }

    override fun draw() {
        val hashList = getHashList()
        var count: Float = 0.0f
        val hashListSize = hashList.size.toFloat()
        background(256.0f,256f,256f)
        noFill()
        translate(width/2.toFloat(),height/2.toFloat())
        ellipse(0.0f, 0.0f, 300.0f, 300.0f)
        val num = (PI*2.0f)/hashListSize
        for(i in hashList) {
            rotate(num*count)
            ellipse(0.0f, -(height/2.0f)+((height/2.0f)-150.0f), 20.0f, 20.0f)
            rotate(-num*count)
            count++
        }
    }

    init {
        conn = DB()
    }

    fun run(args: Array<String>) = main(Canvas().javaClass.name)
}