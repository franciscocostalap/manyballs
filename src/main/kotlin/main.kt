import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 *
 * Moves the racket on mouse move.
 * Adds one ball to the game every 5 seconds.
 * Moves every ball in the game, and bounces the ball
 * if it hits the racket.
 * Closes the window if every ball leaves game's area.
 */
fun main() {
    onStart{
        var game = Game(Area(WIDTH,HEIGHT),emptyList(), Racket(RACKET_X))
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = if(me.x in RACKET_WIDTH/2..game.area.width-RACKET_WIDTH/2)
                    Game(game.area, game.balls, Racket(me.x - RACKET_WIDTH/2)) else game
            arena.drawGame(game)
        }
        arena.onTimeProgress(10) {
            val movedBalls = game.moveBalls()
            if (game.balls.isNotEmpty() && movedBalls.isEmpty())
                arena.close()
            else{ game = Game(game.area, movedBalls, game.racket)
                  arena.drawGame(game)
            }
        }
        arena.onTimeProgress(5000){
            game = game.newBall()
        }
    }
    onFinish {}
}
