import pt.isel.canvas.*

/**
 * Bricks width and height in pixels
 */
const val BRICK_WIDTH = 32
const val BRICK_HEIGHT = 15

/**
 * Bricks border width and height and thickness in pixels
 */
const val BORDER_WIDTH = BRICK_WIDTH -1
const val BORDER_HEIGHT = BRICK_HEIGHT -1
const val BORDER_THICK = 1

/**
 * Brick information.
 *
 * Position ([x],[y]) and [type].
 * @property x Horizontal brick position in bricks
 * @property y Vertical brick position in bricks
 * @property type Brick's type
 */
data class Brick(val x:Int, val y:Int, val type:BricksType, val hitCount:Int=0)

/**
 * Colors in hexadecimal
 */
const val ORANGE = 0xFFA500
const val GOLD = 0xFFD700
const val SILVER = 0xC0C0C0

/**
 * Brick types.
 *
 * @property color Brick possible colors
 *
 * @property points Points that bricks add to the score when hit
 *
 * @property hits Hits that the bricks take to disappear
 * "-1" hits means that it won't disappear if hit
 */
enum class BricksType(val color: Int, val points:Int, val hits:Int){White(WHITE, 1,1), Orange(ORANGE, 2, 1),
    Cyan(CYAN, 3,1), Green(GREEN, 4, 1), Red(RED, 6,1 ), Blue(BLUE, 7,1),
    Magenta(MAGENTA, 8,1), Yellow(YELLOW, 9,1), Gold(GOLD, 0,-1), Silver(SILVER, 0, 2)
}

/**
 * Draws a Brick
 *
 * @receiver [Canvas] Where it draws
 *
 * @param b Brick to draw
 *
 */
fun Canvas.drawBrick(b:Brick){
    val x = b.x * BRICK_WIDTH
    val y = b.y * BRICK_HEIGHT
    drawRect(x, y, BRICK_WIDTH, BRICK_HEIGHT, b.type.color)
    drawRect(x+BORDER_THICK, y+BORDER_THICK, BORDER_WIDTH, BORDER_HEIGHT, BLACK, BORDER_THICK)
}

/**
 *  Creates a list with all of a brick in a column with coordinates limited to the ranges.
 *
 *  @param xRange Horizontal range for the brick columns.
 *  @param yRange Vertical range for the brick lines.
 *  @param l List of types of bricks that make up a line.(one Element, makes up one line)
 *
 *  @return A list of bricks.
 */
fun getBrickLines(xRange: IntRange, yRange:IntRange, l:List<BricksType>):List<Brick>{
    var brickList = emptyList<Brick>()
    var index = 0
    yRange.forEach {y->
        xRange.forEach {x->
            brickList = brickList +
                    when(l[index]){
                        BricksType.White -> Brick(x, y,BricksType.White)
                        BricksType.Gold-> Brick(x, y,BricksType.Gold)
                        BricksType.Magenta-> Brick(x, y,BricksType.Magenta)
                        BricksType.Red-> Brick(x, y,BricksType.Red)
                        BricksType.Silver-> Brick(x, y,BricksType.Silver)
                        BricksType.Blue-> Brick(x, y,BricksType.Blue)
                        BricksType.Green  -> Brick(x, y,BricksType.Green)
                        BricksType.Yellow -> Brick(x, y,BricksType.Yellow)
                        BricksType.Orange -> Brick(x, y,BricksType.Orange)
                        BricksType.Cyan -> Brick(x, y,BricksType.Cyan)
                    }
        }
        ++index
    }
    return brickList
}

/**
 * Lists of the middle column types of bricks to use in [getBrickLines] function (without the first line)
 */
val middleBrickLine = listOf<BricksType>(
    BricksType.Orange,
    BricksType.Cyan,
    BricksType.Green,
    BricksType.Red,
    BricksType.Blue,
    BricksType.Magenta,
    BricksType.Silver)

/**
 *Lists of the left and right columns types of bricks to use in [getBrickLines] function
 */
val leftAndRightLines = listOf<BricksType>(
    BricksType.Yellow,
    BricksType.Magenta,
    BricksType.Blue,
    BricksType.Red,
    BricksType.Green,
    BricksType.Cyan,
    BricksType.Orange,
    BricksType.White)

/**
 * List that contains all the bricks from the middle columns first line
 */
val middleCollumnsFirstLine = listOf<Brick>(
    Brick(5, 3, BricksType.White), Brick(6, 3, BricksType.Gold), Brick(7, 3, BricksType.White))
/**
 * List that contains all the bricks from the middle columns
 */
val middleBrickFullLines = getBrickLines(5..7, 4..10, middleBrickLine) + middleCollumnsFirstLine


/**
 * Starting Bricks in the current and only level
 */
val levelOneBricks:List<Brick> = getBrickLines(1..3,3..10, leftAndRightLines) +
        getBrickLines(9..11, 3..10, leftAndRightLines) + middleBrickFullLines

/**
 * List that contains all the unbreakable bricks in level one.
 */
val unbreakableBricks:List<Brick> = levelOneBricks.filter {b-> b.type.hits == -1}




