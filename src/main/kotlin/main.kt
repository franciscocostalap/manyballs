import pt.isel.canvas.*


val ARENA_X = 0..400

val ARENA_Y = 0..600

/**
 * Program entry point for a version of MultiBall
 */
fun main() {
    onStart{
        val arena = Canvas(ARENA_X.last, ARENA_Y.last, BLACK)
        var game = Game(emptyList(), Racket(RACKET_X))
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = if (me.x - RACKET_WIDTH/2 > ARENA_X.first && me.x + RACKET_WIDTH/2 < ARENA_X.last)
                    Game(game.balls, Racket(me.x - RACKET_WIDTH/2)) else game
                arena.drawGame(game)
        }
        arena.onTimeProgress(10){
            game = Game(game.moveBalls(), game.racket)
            arena.drawGame(game)
        }
        arena.onTimeProgress(5000){
            game = game.newBall()
            println(game.balls)
        }
    }
    onFinish {}
}