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
 * Ball's horizontal coordinate displacement range in pixels.
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
 * Limits a value to an Integer range.
 *
 * @param value An integer
 *
 * @param range An integer range
 *
 * @return [value] limited to the range
 */
fun limitTo(value:Int, range:IntRange) = when {
    value in range   -> value
    value>range.last -> range.last
    else             -> range.first
}

/**
 * Moves a ball based on it's displacement and makes it bounce on window borders and racket.
 *
 * @receiver A ball.
 * @param g  The game.
 *
 *
 * @return A moved ball.
 */
fun Ball.move(g:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    val racketEndX = g.racket.x + RACKET_WIDTH
    val racketHit = (newY + RADIUS) in (RACKET_Y..RACKET_Y + RACKET_HEIGHT) && this.dy > 0
                && (newX + RADIUS in g.racket.x..racketEndX ||newX - RADIUS in g.racket.x..racketEndX)
    /**
     * Checks if the ball collides with the racket, and alters the ball's displacement
     * based on which part of the racket the ball collided with.
     *
     * @return The same ball with it's displacement altered.
     */
    fun collide():Ball{
        // Racket Parts horizontal position based on Racket's horizontal position
        val centerRectX = g.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH
        val rightinterX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
        val leftinterX = g.racket.x + CORNER_WIDTH
        val rightcornerX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH
        // Collision Conditions
        val leftCornerHit = newX in g.racket.x until leftinterX || newX + RADIUS in g.racket.x until leftinterX
        val rightCornerHit = newX in rightcornerX..racketEndX || newX - RADIUS in rightcornerX..racketEndX
        val leftInterHit = newX in leftinterX until centerRectX
        val rightInterHit = newX in rightinterX until rightcornerX
        return Ball(x, y,
            when {
                rightCornerHit -> limitTo(dx + CORNER_ACCEL, DELTAX)
                leftCornerHit  -> limitTo(dx - CORNER_ACCEL, DELTAX)
                leftInterHit   -> limitTo(dx - INTERMEDIATE_ACCEL, DELTAX)
                rightInterHit  -> limitTo(dx + INTERMEDIATE_ACCEL, DELTAX)
                else -> dx
            }, -dy)
    }
    return if (racketHit) collide()
    else when {
        newX !in 0 + RADIUS..g.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
        newY < 0 + RADIUS                           -> Ball(newX, y, dx, -dy)
        else                                        -> Ball(newX, newY, dx, dy)
    }
}
