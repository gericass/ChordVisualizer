package canvas


import java.awt.*

internal class MyCanvas : Canvas() {
    override fun paint(g: Graphics) {
        g.drawLine(1, 1, 500, 500)
    }
}
internal class LineTest : Frame("LineTest") {
    init {
        setSize(500, 500)
        layout = BorderLayout()
        val mc1 = MyCanvas()
        add(mc1, BorderLayout.CENTER)
        show()
    }
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            LineTest()
        }
    }
}