import pt.isel.canvas.*

const val BRICK_WIDTH = 32

const val BRICK_HEIGHT = 15

const val BORDER_WIDTH = BRICK_WIDTH -1

const val BORDER_HEIGHT = BRICK_HEIGHT -1




/**
 * Brick information.
 *
 * Position ([x],[y]) and [type].
 * @property x Horizontal brick position
 * @property y Vertical brick position
 * @property type Brick's type
 */
data class Brick(val x:Int, val y:Int, val type:Bricks)

const val ORANGE = 0xFFA500

const val GOLD = 0xFFD700

const val SILVER = 0xC0C0C0

enum class Bricks(val color: Int, val points:Int){White(WHITE, 1), Orange(ORANGE, 2), Cyan(CYAN, 3),
    Green(GREEN, 4), Red(RED, 6), Blue(BLUE, 7), Magenta(MAGENTA, 8), Yellow(YELLOW, 9),
    Gold(GOLD, 0), Silver(SILVER, 0)
}

/**
 * Draws a Brick
 *
 * @receiver [Canvas] Where it draws
 *
 * @param b Brickc to draw
 *
 */
fun Canvas.drawBrick(b:Brick){
    val x = b.x * BRICK_WIDTH
    val y = b.y * BRICK_HEIGHT
    drawRect(x, y, BRICK_WIDTH, BRICK_HEIGHT, b.type.color)
    drawRect(x+1, y+1, BORDER_WIDTH, BORDER_HEIGHT, BLACK, 1)
}

fun getColumnBricks(xRange: IntRange, yRange:IntRange, l:List<Int>):List<Brick>{
    var brickList = emptyList<Brick>()
    var index = 0
    yRange.forEach {y->
        xRange.forEach {x->
                brickList = brickList + Brick(x, y,
                    when(l[index]){
                        WHITE -> Bricks.White
                        GOLD-> Bricks.Gold
                        MAGENTA-> Bricks.Magenta
                        RED-> Bricks.Red
                        SILVER-> Bricks.Silver
                        BLUE-> Bricks.Blue
                        GREEN  -> Bricks.Green
                        YELLOW -> Bricks.Yellow
                        ORANGE -> Bricks.Orange
                        else -> Bricks.Cyan
                    })
        }
        ++index
    }
    return brickList
}

val middleBrickCollumn = listOf<Int>(ORANGE, CYAN, GREEN, RED, BLUE, MAGENTA, SILVER)
val middleCollumnFirstLine = listOf<Brick>(Brick(5, 3, Bricks.White), Brick(6, 3, Bricks.Gold),
    Brick(7, 3, Bricks.White))

val leftAndRightCollumns = listOf<Int>(YELLOW, MAGENTA, BLUE, RED, GREEN, CYAN, ORANGE, WHITE)

val levelOneBricks:List<Brick> = getColumnBricks(1..3,3..10, leftAndRightCollumns)+
        getColumnBricks(9..11, 3..10, leftAndRightCollumns) +
        getColumnBricks(5..7, 4..10, middleBrickCollumn) + middleCollumnFirstLine