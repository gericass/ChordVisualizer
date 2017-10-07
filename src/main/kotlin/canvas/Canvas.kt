package canvas

import processing.core.PApplet
import db.*

class Canvas() : PApplet() {

    private var conn: DB

    private var hashList: List<String> = listOf()

    private fun setHashList() {
        val hashes = mutableListOf<String>()
        val nodeList = conn.getAllNodes()
        for (i in nodeList) {
            hashes.add(i.hash)
        }
        hashList = hashes.toList().sorted()

    }

    private fun drawBezier(hashListSize: Float) {

        val angle = (PI * 2.0f) / hashListSize
        var node: Node
        var successorIndex: Int
        var predecessorIndex: Int
        for (i in 0..hashListSize.toInt() - 1) {
            node = conn.getNodeByHash(hashList.get(i)) // ifの中に書かないと重い
            val x = 150.0f * cos((angle * i) - PI / 2) //対象ノードの中心のx座標
            val y = 150.0f * sin((angle * i) - PI / 2) //対象ノードの中心のy座標
            successorIndex = hashList.indexOf(node.successor)
            predecessorIndex = hashList.indexOf(node.predecessor)
            val sucX = 150.0f * cos((angle * successorIndex) - PI / 2)
            val sucY = 150.0f * sin((angle * successorIndex) - PI / 2)

            bezier(x, y,  x+10f, y+5f,  x+30f, y+70f,  sucX, sucY)
        }

    }

    private fun showNodeDetails(hashListSize: Float) {
        val angle = (PI * 2.0f) / hashListSize
        var node: Node
        for (i in 0..hashListSize.toInt() - 1) {
            val mX = (mouseX - width / 2).toFloat()
            val mY = (mouseY - height / 2).toFloat()
            val x = 150.0f * cos((angle * i) - PI / 2) //対象ノードの中心のx座標
            val y = 150.0f * sin((angle * i) - PI / 2) //対象ノードの中心のy座標

            if (x - 10 <= mX && mX < x + 10 && y - 10 <= mY && mY < y + 10) {
                node = conn.getNodeByHash(hashList.get(i))
                rect(mX, mY - 50, 300f, 50f)
                fill(77f,179f,87f)
                text("Hash: " + node.hash.slice(0..32) + "\n          " + node.hash.slice(33..63), mX + 10, mY - 30)
                rotate(angle * i)
                ellipse(0.0f, -(height / 2.0f) + ((height / 2.0f) - 150.0f), 20.0f, 20.0f)
                rotate(-angle * i)
                rotate(angle * hashList.indexOf(node.successor))
                fill(0f, 102f, 153f)
                ellipse(0.0f, -(height / 2.0f) + ((height / 2.0f) - 150.0f), 20.0f, 20.0f)
                rotate(-angle * hashList.indexOf(node.successor))
                rotate(angle * hashList.indexOf(node.predecessor))
                fill(184f,48f,29f)
                ellipse(0.0f, -(height / 2.0f) + ((height / 2.0f) - 150.0f), 20.0f, 20.0f)
                rotate(-angle * hashList.indexOf(node.predecessor))
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
        setHashList()

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
        drawBezier(hashListSize)
        showNodeDetails(hashListSize)

    }

    init {
        conn = DB()
    }

    fun run(args: Array<String>) = main(Canvas().javaClass.name)
}