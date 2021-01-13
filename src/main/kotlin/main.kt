import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 *
 * Moves the racket on mouse move.
 *
 * Adds one ball to the game on mouse click.
 *
 * Moves every ball in the game, and bounces the ball
 * if it hits the racket or Window upper, left or right borders.
 *
 * Closes the window if every ball leaves the game Area through
 * window's bottom border.
 */
 fun main() {
    onStart {
        loadSounds("start")
        var game = Game(startingArea, startingBalls, startingRacket)
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        arena.drawGame(game)
        playSound("start")
        arena.onMouseMove { me ->
            game = game.moveRacket(me)
            arena.drawGame(game)
        }
        arena.onMouseDown {
            game = when{
                game.balls.isEmpty() -> game.newBall()
                game.racket.ballOn   -> game.throwBall()
                else                 -> game
            }
        }
        arena.onTimeProgress(10) {
            game = game.move()
            arena.drawGame(game)
        }
    }
    onFinish {}
 }






