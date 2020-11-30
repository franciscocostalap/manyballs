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


const val WIDTH = 400
const val HEIGHT = 600

fun Canvas.drawGame(g:Game){
    erase()
    drawText(g.area.width/2, g.area.height, g.balls.size.toString(), WHITE, 30)
    g.balls.forEach {drawBall(it)}
    drawRacket(g.racket)

}

/**
 * Adds one more ball to the game.
 *
 * @receiver The Game
 *
 * @return Games's list of balls with one more ball.
 */
fun Game.newBall() = Game(this.area,this.balls + Ball(area.width/2, area.height + RADIUS, DELTAX.random(), DELTAY), racket)

fun Game.moveBalls():List<Ball>{
    val filteredBalls = balls.filter {it.y in 0..(this.area.height + 2*RADIUS)}
    return if (balls.any {it.y !in 0..area.height + 2*RADIUS})filteredBalls else balls.map {it.move(this)}
}
