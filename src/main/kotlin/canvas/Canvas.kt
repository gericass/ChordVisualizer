package canvas


import java.*
import processing.core.PApplet
import db.*

class Canvas() : PApplet() {

    private var conn: DB

    private fun getHashList(): List<String> {
        val hashes = mutableListOf<String>()
        val nodeList = conn.getAllNodes()
        for (i in nodeList) {
            hashes.add(i.hash)
        }

        return hashes.sorted()
    }

    private fun showNodeDetails(hashListSize: Float) {
        val angle = (PI * 2.0f) / hashListSize
        var node: Node
        for (i in 0..hashListSize.toInt() - 1) {
            val mX = (mouseX - width / 2).toFloat()
            val mY = (mouseY - height / 2).toFloat()
            val x = 150.0f * cos((angle * i) - PI / 2)
            val y = 150.0f * sin((angle * i) - PI / 2)
            if (x - 10 <= mX && mX < x + 10 && y - 10 <= mY && mY < y + 10) {
                rect(mX, mY - 50, 300f, 50f)
                node = conn.getNodeByHash(getHashList().get(i))
                fill(0f, 102f, 153f)
                text("Hash: " + node.hash.slice(0..32) + "\n          " + node.hash.slice(33..63), mX + 10, mY - 30)
                rotate(angle * i)
                ellipse(0.0f, -(height / 2.0f) + ((height / 2.0f) - 150.0f), 20.0f, 20.0f)
                rotate(-angle * i)
            }
        }
    }

    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        background(256.0f, 256.0f, 256.0f)
    }

    override fun draw() {
        val hashList = getHashList()
        var count = 0.0f
        val hashListSize = hashList.size.toFloat()

        // 背景の初期化
        background(256.0f, 256.0f, 256.0f)

        // 大きい円
        noFill()
        translate(width / 2.toFloat(), height / 2.toFloat())
        ellipse(0.0f, 0.0f, 300.0f, 300.0f)

        // ノード
        val angle = (PI * 2.0f) / hashListSize
        for (i in hashList) {
            rotate(angle * count)
            ellipse(0.0f, -(height / 2.0f) + ((height / 2.0f) - 150.0f), 20.0f, 20.0f)
            rotate(-angle * count)
            count++
        }
        showNodeDetails(hashListSize)

    }

    init {
        conn = DB()
    }

    fun run(args: Array<String>) = main(Canvas().javaClass.name)
}