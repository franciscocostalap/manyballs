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

fun Ball.move(game:Game):Ball{
    val newX = x + dx
    val newY = y + dy
    fun centerHit():Boolean =
        newX in game.racket.x + CORNER_WIDTH +INTERMEDIATE_WIDTH..(game.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH)

    fun rightCornerHit():Boolean =
        (newX - RADIUS in (game.racket.x + RACKET_WIDTH - CORNER_WIDTH)..(game.racket.x + RACKET_WIDTH)
        || newX in (game.racket.x + RACKET_WIDTH - CORNER_WIDTH)..(game.racket.x + RACKET_WIDTH))
    fun leftCornerHit():Boolean =
        (newX in game.racket.x..(game.racket.x + CORNER_WIDTH)
        || newX + RADIUS in game.racket.x..(game.racket.x + CORNER_WIDTH))
    fun leftInterHit():Boolean =
        newX in game.racket.x..(game.racket.x + CORNER_WIDTH + INTERMEDIATE_WIDTH)
    fun rightInterHit():Boolean =
        newX in (game.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH)..(game.racket.x  + RACKET_WIDTH - CORNER_WIDTH)
    return when {
        newX !in 0 + RADIUS..game.area.width - RADIUS  -> Ball(x, newY, -dx, dy)
        newY < 0 + RADIUS -> Ball(newX, y, dx, -dy)
        centerHit()       -> Ball(x, y, dx, -dy)
        leftCornerHit()   -> Ball(x, y, if(dx - CORNER_ACCEL > DELTAX.first) dx - CORNER_ACCEL else  DELTAX.first, -dy)
        rightCornerHit()  -> Ball(x, y, if(dx + CORNER_ACCEL < DELTAX.last) dx + CORNER_ACCEL else  DELTAX.last, -dy)
        leftInterHit()    -> Ball(x, y, if(dx - INTERMEDIATE_ACCEL > DELTAX.first) dx - INTERMEDIATE_ACCEL
                                        else  DELTAX.first, -dy)
        rightInterHit()   -> Ball(x, y, if(dx + INTERMEDIATE_ACCEL < DELTAX.last) dx + INTERMEDIATE_ACCEL
                                        else  DELTAX.last, -dy)
        else              -> Ball(newX, newY, dx, dy)
    }
}