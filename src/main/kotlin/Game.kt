import pt.isel.canvas.Canvas
import pt.isel.canvas.MouseEvent
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
 * Draws the game's content.(Ball's counter, balls in game, and Racket.
 *
 * @receiver Canvas.
 *
 * @param g Game to draw.
 */
fun Canvas.drawGame(g:Game){
    erase()
    drawText(g.area.width/2, g.area.height, g.balls.size.toString(), WHITE, 30)
    g.balls.forEach {ball ->drawBall(ball)}
    drawArea(g.area)
    drawRacket(g.racket)

}

/**
 * Adds one more ball to the game.
 *
 * @receiver The game
 *
 * @return Games's list of balls with one more ball.
 */
fun Game.newBall():Game = if (balls.isEmpty())
    Game(area,balls + Ball(racket.x+RACKET_WIDTH/2, RACKET_Y-RADIUS, 0, 0), racket)
    else this


/**
 *  Moves every ball inside Game's list of balls,
 *  and removes from it, the balls that leave
 *  the window from it's bottom border.
 *
 *  @receiver The game.
 *
 *  @return A list of balls with the moved balls in game.
 */
fun Game.moveBalls() = if (racket.ballOn) balls.map{it.move(this)}.filter { it.y in 0..(area.height + 2*RADIUS)}
else balls

fun Game.moveRacket(mEvent:MouseEvent):Game {
    var racketNewX = racket.x
    val newBallsX = racket.x+RACKET_WIDTH/2
    val newBalls = balls.map {Ball(newBallsX,  RACKET_Y-RADIUS, 0, 0)}
    if (mEvent.x in RACKET_WIDTH / 2..area.width - RACKET_WIDTH / 2){
         racketNewX = mEvent.x - RACKET_WIDTH / 2
    }
    return if(racket.ballOn)
        Game(area, newBalls, Racket(racketNewX, racket.width))
    else this

}




