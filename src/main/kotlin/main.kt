import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 */
fun main() {
    onStart{
        var game = Game(Area(WIDTH,HEIGHT),emptyList(), Racket(RACKET_X))
        val arena = Canvas(game.area.width, game.area.height, BLACK)
        arena.drawGame(game)
        arena.onMouseMove { me ->
            game = if (me.x - RACKET_WIDTH/2 > 0 && me.x + RACKET_WIDTH/2 < game.area.width)
                    Game(game.area, game.balls, Racket(me.x - RACKET_WIDTH/2)) else game
            arena.drawGame(game)
        }
        arena.onTimeProgress(10) {
            game = Game(game.area, game.moveBalls(), game.racket)
            if(game.balls.isEmpty() && it > 5100)
                arena.close()
            arena.drawGame(game)
        }
        arena.onTimeProgress(5000){
            game = game.newBall()
        }
    }
    onFinish {}
}