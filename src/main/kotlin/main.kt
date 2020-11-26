import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 */
fun main() {
    onStart{
        val arena = Canvas(400, 600, BLACK)
        var rck = Racket(RACKET_X, RACKET_Y)
        arena.drawRacket(rck)
        arena.onMouseMove { me ->
            rck = Racket(me.x,RACKET_Y)
            arena.erase()
            arena.drawRacket(rck)
        }
    }
    onFinish {}
}