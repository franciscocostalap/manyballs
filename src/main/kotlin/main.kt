import pt.isel.canvas.*

/**
 * Program entry point for a version of Arkanoid
 *
 * Moves the racket and the ball on mouse move.
 *
 * The ball starts to move on mouse click.
 *
 * Moves every ball in the game, and it bounces on the borders
 * if it hits the racket or Window upper, left or right borders.
 *
 * If the ball gets throughout Area's bottom border, if there are lives remaining
 * the game adds another ball on top of the racket and the player loses one life.
 *
 * If there are no lives remaining, the game ends with an on screen message "Game Over"
 */
 fun main() {
    onStart {
        var game = Game(startingArea, startingBalls, startingRacket, sound=false)
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        //loadSounds( "racketHit", "brickHit")
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = game.moveRacket(me)
            arena.drawGame(game)
        }
        arena.onMouseDown {
            game = when{
                game.balls.isEmpty()        -> game.newBall()
                game.balls.any{it.onRacket} -> game.throwBall()
                else                        -> game
            }
        }
        arena.onTimeProgress(10) {
            game = game.update()
            arena.drawGame(game)
        }
        arena.onKeyPressed { ke ->
            if (ke.char in "sS")
                game = Game(game.area, game.balls, game.racket, game.over, game.finish, sound=!game.sound)
        }
    }
    onFinish {}
 }







