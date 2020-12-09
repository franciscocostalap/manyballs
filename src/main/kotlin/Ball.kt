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
 * Ball's horizontal coordinate starting displacement in pixels.
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
 * TODO: Ball.move DOC e deixar mais limpo
 */
fun Ball.move(game:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    val centerX = game.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH
    val rightInterX = game.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
    val leftInterX = game.racket.x + CORNER_WIDTH
    val rightCornerX = game.racket.x + RACKET_WIDTH - CORNER_WIDTH
    val racketEndX = game.racket.x + RACKET_WIDTH
    if(newY + RADIUS in RACKET_Y..RACKET_Y + RACKET_HEIGHT && this.dy > 0) {
        return when {
            // center
            newX in centerX..rightInterX
            -> Ball(x, y, dx, -dy)
            // right corner
            newX in rightCornerX until racketEndX || newX - RADIUS in rightCornerX until racketEndX
            -> Ball(x, y, if (dx + CORNER_ACCEL < DELTAX.last) dx + CORNER_ACCEL else DELTAX.last, -dy)
            //left corner
            newX in game.racket.x until leftInterX || newX + RADIUS in game.racket.x until leftInterX
            -> Ball(x, y, if (dx - CORNER_ACCEL > DELTAX.first) dx - CORNER_ACCEL else DELTAX.first, -dy)
            //left inter hit
            newX in leftInterX until centerX
            -> Ball(x, y, if (dx - INTERMEDIATE_ACCEL > DELTAX.first) dx - INTERMEDIATE_ACCEL else DELTAX.first, -dy)
            //Right inter hit
            newX in rightInterX until rightCornerX
            -> Ball(x, y, if (dx + INTERMEDIATE_ACCEL < DELTAX.last) dx + INTERMEDIATE_ACCEL else DELTAX.last, -dy)
            else -> Ball(newX, newY, dx, dy)
        }
    }
    else return when{
            newX !in 0 + RADIUS..game.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
            newY < 0 + RADIUS                              -> Ball(newX, y, dx, -dy)
            else                                           -> Ball(newX, newY, dx, dy)
        }
}