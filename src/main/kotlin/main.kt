import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 */
fun main() {
    onStart{
        val arena = Canvas(400, 600, BLACK)
        var rck = Racket(RACKET_X)
        val ball = Ball(arena.width/2, arena.height/2, DELTAX, DELTAY)
        arena.drawRacket(rck)
        arena.drawBall(ball)

        arena.onMouseMove { me ->
            rck = Racket(me.x - RACKET_WIDTH/2)
            arena.erase()
            arena.drawRacket(rck)
            arena.drawBall(ball)
            println(DELTAX)
        }
    }
    onFinish {}
}