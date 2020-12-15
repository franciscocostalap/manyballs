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
 val DELTAX = -0..0

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



fun Ball.move(g:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    val racketEndX = g.racket.x + RACKET_WIDTH
    fun Ball.collision():Ball{
        val centerX = g.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH
        val rightinterX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
        val leftinterX = g.racket.x + CORNER_WIDTH
        val rightcornerX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH
        return Ball(x, y,
            when {
                newX in rightcornerX..racketEndX
                || newX - RADIUS in rightcornerX..racketEndX    -> limit(dx + CORNER_ACCEL, DELTAX.first, DELTAX.last)
                newX in g.racket.x until leftinterX
                || newX + RADIUS in g.racket.x until leftinterX -> limit(dx - CORNER_ACCEL, DELTAX.first, DELTAX.last)
                newX in leftinterX until centerX                -> limit(dx - INTERMEDIATE_ACCEL, DELTAX.first, DELTAX.last)
                newX in rightinterX until rightcornerX          -> limit(dx + INTERMEDIATE_ACCEL, DELTAX.first, DELTAX.last)
                else -> dx
            },
            -dy)
    }
    return if (newY + RADIUS in RACKET_Y..RACKET_Y + RACKET_HEIGHT && this.dy > 0
              && (newX + RADIUS in g.racket.x..racketEndX ||newX - RADIUS in g.racket.x..racketEndX )) collision()
    else when {
        newX !in 0 + RADIUS..g.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
        newY < 0 + RADIUS                           -> Ball(newX, y, dx, -dy)
        else                                        -> Ball(newX, newY, dx, dy)
    }
}

