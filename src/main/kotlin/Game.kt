import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Game's information.
 *
 * @property balls List of Balls. (Ball type)
 *
 * @property racket Game's racket.
 */
data class Game(val area:Area, val balls:List<Ball>, val racket: Racket)

/**
 * Game's Area information.
 *
 * @property width game's window width in pixels.
 *
 * @property height game's window height in pixels.
 */
data class Area(val width:Int, val height:Int)

const val WIDTH = 400
const val HEIGHT = 600

/**
 * Draws the game's content.(Balls in game, ball's counter and Racket.
 *
 * @receiver Canvas.
 *
 * @param g Game to draw.
 */
fun Canvas.drawGame(g:Game){
    erase()
    drawText(g.area.width/2, g.area.height, g.balls.size.toString(), WHITE, 30)
    g.balls.forEach {drawBall(it)}
    drawRacket(g.racket)

}

/**
 * Adds one more ball to the game.
 *
 * @receiver The game
 *
 * @return Games's list of balls with one more ball.
 */
fun Game.newBall():Game = Game(area,balls + Ball(area.width/2, area.height, DELTAX.random(), DELTAY), racket)


/**
 *  Moves every ball inside Game's list of balls,
 *  and removes from it, the balls that leave
 *  the window from it's bottom border.
 *
 *  @receiver The game.
 *
 *  @return A list of balls with the moved balls in game.
 */
fun Game.moveBalls() = balls.map{it.move(this)}.filter { it.y in 0..(area.height + 2*RADIUS)}

