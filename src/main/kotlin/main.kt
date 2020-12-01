import pt.isel.canvas.*

data class Area(val width:Int, val height:Int)


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
        arena.onTime(5000) {
            arena.onTimeProgress(10) {
                if(game.balls.isEmpty())
                    arena.close()
                //TODO: filterBalls() dúvida
                game = Game(game.area, game.balls.map{it.move(game)}, game.racket)
                game = Game(game.area, game.filterBalls(), game.racket)
                arena.drawGame(game)
            }
        }
        arena.onTimeProgress(5000){
            game = game.newBall()
        }
    }
    onFinish {}
}