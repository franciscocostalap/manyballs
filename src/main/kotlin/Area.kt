import pt.isel.canvas.*


/**
 * Game's Area information.
 *
 * @property width game's window width in pixels.
 *
 * @property height game's window height in pixels.
 */
data class Area(val width:Int, val height:Int, val bricks: List<Brick>, val lives:Int = 5)

/**
 * Window (Area) Dimensions
 */
const val WIDTH = 13 * BRICK_WIDTH
const val HEIGHT = 600


const val LIVES_Y = HEIGHT - 10

fun Canvas.drawArea(a:Area){
    a.bricks.forEach {b -> drawBrick(b)}
    (0 until a.lives).forEach {l -> drawBall(Ball(3*(l+1)*RADIUS, LIVES_Y, 0, 0))}
}






