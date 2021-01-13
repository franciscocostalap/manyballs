import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas
import kotlin.math.sqrt

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
 * Starting Ball's List
 */
val startingBalls = emptyList<Ball>() + Ball(RACKET_X+RACKET_WIDTH/2, RACKET_Y - RADIUS, 0, 0)

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
        val rightInterX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
        val leftInterX = g.racket.x + CORNER_WIDTH
        val rightCornerX = g.racket.x + RACKET_WIDTH - CORNER_WIDTH
        // Collision Conditions
        val leftCornerHit:Boolean = newX in g.racket.x until leftInterX || newX + RADIUS in g.racket.x until leftInterX
        val rightCornerHit:Boolean = newX in rightCornerX..racketEndX || newX - RADIUS in rightCornerX..racketEndX
        val leftInterHit:Boolean = newX in leftInterX until centerRectX
        val rightInterHit:Boolean = newX in rightInterX until rightCornerX
        return Ball(x, y,
            when {
                rightCornerHit -> limitTo(dx + CORNER_ACCEL, DELTAX)
                leftCornerHit  -> limitTo(dx - CORNER_ACCEL, DELTAX)
                leftInterHit   -> limitTo(dx - INTERMEDIATE_ACCEL, DELTAX)
                rightInterHit  -> limitTo(dx + INTERMEDIATE_ACCEL, DELTAX)
                else           -> dx
            }, -dy)
    }
    return when {
        newX !in 0 + RADIUS..g.area.width - RADIUS -> Ball(x, newY, -dx, dy)
        newY < 0 + RADIUS                          -> Ball(newX, y, dx, -dy)
        racketHit                                  -> collide()
        else                                       -> Ball(newX, newY, dx, dy)
    }
}


data class BrickCollision(val ball: Ball?, val brick:Brick?)

fun Ball.brickCollide(bricks: List<Brick>):BrickCollision{
    bricks.forEach{ brick ->
        val xBrick = brick.x * BRICK_WIDTH
        val yBrick = brick.y * BRICK_HEIGHT
        val closestX = limitTo(this.x, xBrick..xBrick+BRICK_WIDTH)
        val closestY = limitTo(this.y, yBrick..yBrick+BRICK_HEIGHT)
        val collided = distance(this.x, this.y, closestX, closestY) <= RADIUS
        if(collided){
            if(closestX == xBrick && this.dx > 0 || closestX == xBrick + BRICK_WIDTH && this.dx < 0){
                return BrickCollision(Ball(x, y, -dx, dy), brick)
            }
            if(closestY == yBrick && this.dy > 0 || closestY == yBrick + BRICK_HEIGHT && this.dy < 0){
                return BrickCollision(Ball(x, y, dx, -dy), brick)
            }
        }
    }
    return BrickCollision(null, null)
}


/**
 * Calculates the distance between two coordinates in pixels
 */
fun distance(coord1X:Int, coord1Y:Int, coord2X:Int, coord2Y:Int):Float{
    val xDistance: Int = coord1X - coord2X
    val yDistance: Int = coord1Y - coord2Y
    return sqrt((xDistance.square() + yDistance.square()).toFloat())
}

/**
 * @receiver An Integer
 *
 * @return The integer power 2
 */
fun Int.square() = this * this
