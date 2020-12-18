import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 *
 * Moves the racket on mouse move.
 *
 * Adds one ball to the game every 5 seconds.
 *
 * Moves every ball in the game, and bounces the ball
 * if it hits the racket or Window Upper, left or right borders.
 *
 * Closes the window if every ball leaves the game Area through
 * window's bottom border.
 */
 fun main() {
    onStart {
        var game = Game(Area(WIDTH, HEIGHT, levelOneBricks), emptyList(), Racket(RACKET_X, RACKET_WIDTH))
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = game.moveRacket(me)
            arena.drawGame(game)
        }
        arena.onMouseDown {
                game = game.newBall()
        }
        arena.onTimeProgress(10) {
            game = Game(game.area, game.moveBalls(), game.racket)
            arena.drawGame(game)
        }
    }
    onFinish {}
 }


