import pt.isel.canvas.*

/**
 * Game's information.
 *
 * @property balls List of Balls. (Ball type)
 *
 * @property racket Game's racket.
 *
 * @property over
 */
data class Game(val area:Area,
                val balls:List<Ball>,
                val racket: Racket,
                val over:Boolean = false,
                val finish:Boolean = false,
                val sound:Boolean)
/**
 * Draws the game's content.(Ball's counter, balls in game, racket, game over and finish letters.)
 *
 * @receiver Canvas.
 *
 * @param g Game to draw.
 */
fun Canvas.drawGame(g:Game){
    erase()
    if (g.over) drawText(RADIUS, LIVES_Y, "Game Over", RED, 20)
    if (g.finish) drawText(RACKET_X + RACKET_WIDTH*2, LIVES_Y, "Finish", YELLOW, 20)
    drawText(g.area.width/2, g.area.height, g.area.score.toString(), WHITE, 20)
    g.balls.forEach {ball -> drawBall(ball)}
    drawArea(g.area)
    drawRacket(g.racket)
}


/**
 * Adds one more ball to the game.
 *
 * @receiver The game
 *
 * @return Game's list of balls with one more ball.
 */
fun Game.newBall():Game {
    val newRacket = Racket(racket.x, racket.width)
    val newArea = Area(area.width, area.height, area.bricks, area.lives - 1, area.score)
    return when {
        (area.lives==0) -> Game (area, balls, racket, over = true,sound=sound)
        over || finish -> this
        else -> Game(newArea,
                    balls + Ball(racket.x+RACKET_WIDTH/2, RACKET_Y-RADIUS, 0, 0, true),
                    newRacket,
                    sound=sound)
    }
}
/**
 *  Updates the game with the elements provided by intermediate function [ballMoveAndCollide].
 *  Elements such as Game's Score, list of balls, list of bricks.
 *
 *  Checks also if the game has finished. (finishes when the only bricks remaining are the [unbreakableBricks]).
 *
 *  @receiver The game.
 *
 *  @return A list of balls with the moved balls in game.
 */
fun Game.update():Game{
    if (balls.any { it.onRacket } || over || finish ) return this
    val moveAndCollision = ballMoveAndCollide()
    val newScore = area.score + moveAndCollision.score
    val finishScore = newScore + area.lives * 10
    val newArea = Area(area.width, area.height, moveAndCollision.bricksList, area.lives, newScore)
    val finishArea = Area(area.width, area.height, moveAndCollision.bricksList, area.lives, finishScore)
    val ballFinish = emptyList<Ball>() + Ball(racket.x+ RACKET_WIDTH/2, RACKET_Y - RADIUS, 0, 0, true)
    return if(newArea.bricks.size == unbreakableBricks.size)
        Game(finishArea, ballFinish, racket, over, finish = true, sound)
    else  Game(newArea, moveAndCollision.ballsList, racket, over, finish, sound)

}

/**
 *  UpdateList Information.
 *
 *  @property ballsList List of balls.
 *  @property bricksList List of bricks.
 *  @property score In-game score starting at 0.
 */
data class UpdateList(val ballsList:List<Ball>, val bricksList: List<Brick>, val score:Int=0)

/**
 * Intermediate function that verifies if there were bricks collided, removes the collided bricks from the game
 * and adds the respective score, based on which bricks were hit.
 *
 * Replaces the collided ball in [brickCollide] or [move] functions with the current ball if needed.
 *
 * @receiver The Game
 *
 * @return [UpdateList] type
 */
fun Game.ballMoveAndCollide() :UpdateList {
    val movedBalls = balls.map { it.move(this) }.filter {it.y in 0..(area.height + 2 * RADIUS)}
    var finalBricks = area.bricks
    var score = 0
    val finalBalls = movedBalls.map { ball ->
        val brickCollision = ball.brickCollide(finalBricks, sound)
        val newBricks = finalBricks.map{ brick ->
            val newBrick = Brick(brick.x,brick.y,brick.type,brick.hitCount+1)
            if(brick in brickCollision.bricks) {
                score += newBrick.type.points
                newBrick
            }
            else brick
        }
        finalBricks = newBricks.filter { brick -> brick.hitCount != brick.type.hits }
        brickCollision.ball
    }
    return UpdateList(finalBalls, finalBricks,score)
}

/**
 *  Moves the racket on x axis on mouse move. Moves the ball/s too if the ball/s is/are on the racket.
 *
 * @param mEvent Mouse Event
 *
 * @receiver The game
 *
 * @return The game with the moved racket and ball/s
 */
fun Game.moveRacket(mEvent:MouseEvent):Game{
    val newRacket = Racket(mEvent.x - RACKET_WIDTH / 2, racket.width)
    val newBalls = balls.map {if (it.onRacket) Ball(mEvent.x, RACKET_Y - RADIUS, 0, 0, it.onRacket) else it}
    val racketInArea:Boolean = mEvent.x in RACKET_WIDTH / 2..area.width - RACKET_WIDTH / 2
    return when{
        racketInArea &&  newBalls.all{!it.onRacket} -> Game(area, balls, newRacket, over, finish, sound)
        racketInArea && newBalls.any{it.onRacket}   -> Game(area, newBalls, newRacket, over, finish, sound)
        else                                        -> this
    }
}

/**
 * Throws the ball out of the racket on mouse click when the ball is on the racket.
 *
 * @receiver the Game
 *
 * @return the Game with the thrown ball
 *
 */
fun Game.throwBall():Game{
    val newRacket = Racket(racket.x, racket.width)
    val newBalls = balls.map{ball ->
        if(ball.onRacket) Ball(ball.x, ball.y, ball.dx, DELTA_Y, false) else ball}
    return if (over || finish) this else Game(area, newBalls, newRacket, over, finish, sound)
}
