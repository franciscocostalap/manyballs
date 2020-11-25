import pt.isel.canvas.*

/**
 * Program entry point for a version of MultiBall
 */
fun main() {
    onStart{
        val arena = Canvas(400, 600, BLACK)
        val rck = Racket(RACKET_X, RACKET_Y)
        arena.drawRacket(rck)
    }
    onFinish {}
}