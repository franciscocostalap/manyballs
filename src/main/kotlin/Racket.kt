import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Racket's information.
 *
 * [x]: horizontal position
 * [y]: vertical position
 */
data class Racket(val x:Int, val y:Int)

/**
 * Main rectangle width.
 */
const val RACKET_WIDTH = 90
/**
 * Main rectangle height.
 *
 * Must be even.
 */
const val RACKET_HEIGHT = 10

/**
 * Corner rectangle width and color.
 */
const val CORNER_WIDTH = 10
const val CORNER_COLOR = 0xFF3333

/**
 * Intermediate rectangle width and color.
 */
const val MEDIUM_WIDTH = 15
const val MEDIUM_COLOR = 0xFF6666

/**
 * Racket starting horizontal position.
 */
const val RACKET_X = 155
/**
 * Racket vertical position.
 */
const val RACKET_Y = 550

//TODO: Comentar funçao drawRacket
fun Canvas.drawRacket(rket:Racket) {
    fun drawCorners(rket: Racket) {
        drawRect(rket.x , RACKET_Y, CORNER_WIDTH, RACKET_HEIGHT / 2, CORNER_COLOR)
        drawRect(rket.x + (RACKET_WIDTH - CORNER_WIDTH), RACKET_Y, CORNER_WIDTH, RACKET_HEIGHT / 2, CORNER_COLOR)
    }
    fun drawEdges(rket: Racket){
        drawRect(rket.x + CORNER_WIDTH, rket.y, MEDIUM_WIDTH, RACKET_HEIGHT / 2, MEDIUM_COLOR)
        drawRect(rket.x + RACKET_WIDTH - CORNER_WIDTH - MEDIUM_WIDTH, rket.y, MEDIUM_WIDTH, RACKET_HEIGHT / 2, MEDIUM_COLOR)

    }
    drawRect(rket.x, rket.y, RACKET_WIDTH, RACKET_HEIGHT, WHITE)
    drawCorners(rket)
    drawEdges(rket)
}