import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas

/**
 * Ball information.
 *
 * @property x horizontal coordinate.
 *
 * @property y vertical coordinate.
 *
 * @property dx horizontal coordinate variation.
 *
 * @property dy vertical coordinate variation.
 */
data class Ball(val x:Int, val y:Int, val dx:Int, val dy:Int)
/**
 * Ball's radius.
 */

const val RADIUS = 7

/**
 * Ball's vertical coordinate starting displacement
 */
const val DELTAY = -4

/**
 * Ball's horizontal coordinate starting displacement
 */
val DELTAX = (-6..6).random()

val ARENA_X = 0..400

val ARENA_Y = 0..600


/**
 * Draws a ball.
 *
 * @receiver where it draws.
 *
 * @param b Ball to draw.
 */
fun Canvas.drawBall(b:Ball){
    drawCircle(b.x, b.y, RADIUS, CYAN)
}

fun Ball.move():Ball{
    val newX = x + dx
    val newY = y + dy
    return when {
        newX > ARENA_X.last - RADIUS || newX < ARENA_X.first + RADIUS -> Ball(x, newY, -dx, dy)
        newY > ARENA_Y.last - RADIUS || newY < ARENA_Y.first + RADIUS -> Ball(newX, y, dx, -dy)
        else                                                          -> Ball(newX, newY, dx, dy)
    }
}