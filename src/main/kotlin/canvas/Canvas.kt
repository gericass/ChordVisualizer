package canvas


import java.*
import processing.core.PApplet

class Canvas : PApplet () {
    override fun settings() {
        size(500, 400)
    }
    override fun setup() {
        background(0.0f,123.0f,123.0f)
    }

    override fun draw() {
        noFill()
        ellipse(width/2.toFloat(), height/2.toFloat(), 300.0f, 300.0f)
    }

    fun run(args: Array<String>) = main(Canvas().javaClass.name)
}