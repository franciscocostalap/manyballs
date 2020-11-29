import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Racket's information.
 *
 * @property x horizontal position
 */
data class Racket(val x:Int)

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
const val INTERMEDIATE_WIDTH = 15
const val INTERMEDIATE_COLOR = 0xFF6666

/**
 * Racket starting horizontal position.
 */
const val RACKET_X = 155
/**
 * Racket vertical position.
 */
const val RACKET_Y = 550

/**
 * Draws the racket.
 *
 * @receiver where it draws
 *
 * @param rket Racket to draw
 */
fun Canvas.drawRacket(rket:Racket) {
    /**
     * Draws the corner rectangles of the racket.
     *
     * Only usable inside drawRacket().
     */
    fun drawCorners() {
        drawRect(rket.x , RACKET_Y, CORNER_WIDTH, RACKET_HEIGHT / 2, CORNER_COLOR)
        drawRect(rket.x + (RACKET_WIDTH - CORNER_WIDTH)
                , RACKET_Y
                , CORNER_WIDTH
                , RACKET_HEIGHT / 2
                , CORNER_COLOR)
    }
    /**
     * Draws the intermediate rectangles of the racket.
     *
     * Only usable inside drawRacket().
     */
    fun drawIntermediate(){
        drawRect(rket.x + CORNER_WIDTH, RACKET_Y, INTERMEDIATE_WIDTH, RACKET_HEIGHT / 2, INTERMEDIATE_COLOR)
        drawRect(rket.x + RACKET_WIDTH - CORNER_WIDTH - INTERMEDIATE_WIDTH
                , RACKET_Y
                , INTERMEDIATE_WIDTH
                , RACKET_HEIGHT / 2
                , INTERMEDIATE_COLOR)
    }

    drawRect(rket.x, RACKET_Y, RACKET_WIDTH, RACKET_HEIGHT, WHITE)
    drawCorners()
    drawIntermediate()
}