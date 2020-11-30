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

//Ball(arena.width/2, arena.height/2, DELTAX, DELTAY) Starting ball
const val RADIUS = 7

/**
 * Ball's vertical coordinate starting displacement
 */
const val DELTAY = -4

/**
 * Ball's horizontal coordinate starting displacement
 */
 val DELTAX = -6..6

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

fun Ball.move(game:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    return when {
        newX > ARENA_X.last - RADIUS || newX < ARENA_X.first + RADIUS -> Ball(x, newY, -dx, dy)
        newY < ARENA_Y.first + RADIUS                                 -> Ball(newX, y, dx, -dy)
        newX in game.racket.x..(game.racket.x + RACKET_WIDTH) && newY in RACKET_Y..RACKET_Y + RACKET_HEIGHT
                && this.dy > 0 -> Ball(x, y, dx, -dy)
        else                                                          -> Ball(newX, newY, dx, dy)
    }
}