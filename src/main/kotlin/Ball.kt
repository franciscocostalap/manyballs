import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas

/**
 * Ball information.
 *
 * @property x horizontal coordinate in pixels.
 *
 * @property y vertical coordinate in pixels.
 *
 * @property dx horizontal coordinate variation in pixels.
 *
 * @property dy vertical coordinate variation in pixels.
 */
data class Ball(val x:Int, val y:Int, val dx:Int, val dy:Int)

/**
 * Ball's radius in pixels.
 */
const val RADIUS = 7

/**
 * Ball's vertical coordinate starting displacement in pixels.
 */
const val DELTAY = -4

/**
 * Ball's horizontal coordinate displacement interval in pixels.
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

/**
 *
 */
fun Ball.move(game:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    val centerX = game.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH
    val rightInterX = game.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
    val leftInterX = game.racket.x + CORNER_WIDTH
    val rightCornerX = game.racket.x + RACKET_WIDTH - CORNER_WIDTH
    val racketEndX = game.racket.x + RACKET_WIDTH
    if (newY + RADIUS in RACKET_Y..RACKET_Y + RACKET_HEIGHT && this.dy > 0) {
        return when {
            // center hit
            newX in centerX..rightInterX -> Ball(x, y, dx, -dy)
            // right corner hit
            newX in rightCornerX until racketEndX || newX - RADIUS in rightCornerX until racketEndX
            -> Ball(x, y, limit(dx + CORNER_ACCEL, -6, 6), -dy)
            //left corner hit
            newX in game.racket.x until leftInterX || newX + RADIUS in game.racket.x until leftInterX
            -> Ball(x, y, limit(dx - CORNER_ACCEL, -6 , 6), -dy)
            //left intermediate part hit
            newX in leftInterX until centerX       -> Ball(x, y, limit(dx - INTERMEDIATE_ACCEL, -6, 6), -dy)
            //Right intermediate part hit
            newX in rightInterX until rightCornerX -> Ball(x, y, limit(dx + INTERMEDIATE_ACCEL, -6 , 6), -dy)
            else -> Ball(newX, newY, dx, dy)
        }
    }
    else return when {
            newX !in 0 + RADIUS..game.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
            newY < 0 + RADIUS                              -> Ball(newX, y, dx, -dy)
            else                                           -> Ball(newX, newY, dx, dy)
    }
}

