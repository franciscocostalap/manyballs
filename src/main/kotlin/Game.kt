import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Game's information.
 *
 * @property balls List of Balls. (Ball type)
 *
 * @property racket Game's racket.
 */
data class Game(val balls:List<Ball>, val racket: Racket)

fun Canvas.drawGame(g:Game){
    erase()
    drawText(ARENA_X.last/2, ARENA_Y.last, g.balls.size.toString(), WHITE, 50)
    g.balls.forEach {ball ->  drawBall(ball)}
    drawRacket(g.racket)

}

/**
 * Adds one more ball to the game.
 *
 * @receiver The Game
 *
 * @return Games's list of balls with one more ball.
 */
fun Game.newBall() = Game(this.balls + Ball(ARENA_X.last/2, ARENA_Y.last - RADIUS, DELTAX.random(), DELTAY), racket)

fun Game.moveBalls():List<Ball>{
    val filteredBalls = balls.filter {it.y in ARENA_Y.first..ARENA_Y.last + 2*RADIUS}
    return if (balls.any {it.y !in ARENA_Y.first..ARENA_Y.last + 2*RADIUS})filteredBalls else balls.map {it.move(this)}
}
