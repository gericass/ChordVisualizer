package canvas


import java.*
import processing.core.PApplet

class Canvas : PApplet () {
    override fun settings() {
        size(400, 300)
    }
    override fun setup() {
        background(0.0f)
    }

    override fun draw() {
        fill(255)
        noStroke()
        ellipse(mouseX.toFloat(), mouseY.toFloat(), 10.0f, 10.0f)
    }

    fun run(args: Array<String>) = main(Canvas().javaClass.name)
}