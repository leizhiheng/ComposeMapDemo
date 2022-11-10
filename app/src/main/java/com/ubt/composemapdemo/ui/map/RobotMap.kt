package com.ubtrobot.mapview

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import androidx.compose.ui.graphics.AndroidPath
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Suppress("ArrayInDataClass")
data class RobotMap(
    val id: String,
    val scale: Long,
    val width: Float,
    val height: Float,
    val name: String,
    val createdTime: Long,
    val modifyTime: Long,
    val ground: Ground,
    val rooms: List<Room>? = null,
    val pose: Pose? = null,
    val docker: Pose? = null,
    val description: String? = null,
    val thumbnail: ByteArray? = null,
    val virtualWalls: List<Tracker>? = null,
    val forbiddenAreas: List<Area>? = null,
    val barriers: List<Barrier>? = null,
    val walls: List<Barrier>? = null
) {
    override fun toString(): String {
        return "RobotMap(id='$id', scale=$scale, width=$width, height=$height, name='$name', createdTime=$createdTime, modifyTime=$modifyTime, description=$description, thumbnailSize=${thumbnail?.size},  \npose=$pose, \ndocker=$docker, \nground=$ground, \nrooms=$rooms,\nvirtualWalls=$virtualWalls, \nforbiddenAreas=$forbiddenAreas, \nbarriers=$barriers, \nwalls=$walls)"
    }
}

data class Line(
    var start: PointF,
    var end: PointF
)

interface Element {
    val id: String
    val name: String?
    val description: String?
    val width: Float
    val height: Float
    val originX: Float
    val originY: Float
    val zIndex: Int
    val theta: Float
    val targets: List<String>?
}

data class Pose(
    override val id: String,
    val point: PointF,
    override val theta: Float,
    val type: PoseType,
    override val name: String? = null,
    override val description: String? = null,
    override val width: Float = 0f,
    override val height: Float = 0f,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val targets: List<String>? = null
) : Element

@Suppress("ArrayInDataClass")
data class Ground(
    override val id: String,
    override val name: String?,
    override val description: String? = null,
    override val width: Float,
    override val height: Float,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val theta: Float = 0f,
    override val targets: List<String>? = null,
    private val source: ByteArray
) : Element {

    private val obstaclePoints = mutableListOf<PointF>()
    private val unknownPoints = mutableListOf<PointF>()
    private val freePoints = mutableListOf<PointF>()
    private val bitmap: Bitmap =
        Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)

    init {
        parseSource()
    }

    fun getBitmap(): Bitmap {
        return bitmap
    }

    fun getBarrierPoints(): FloatArray {
        val array = mutableListOf<Float>()
        obstaclePoints.forEach {
            array.add(it.x)
            array.add(it.y)
        }
        return array.toFloatArray()
    }

    fun getUnknownPoints(): FloatArray {
        val array = mutableListOf<Float>()
        unknownPoints.forEach {
            array.add(it.x)
            array.add(it.y)
        }
        return array.toFloatArray()
    }

    fun getFreePoints(): FloatArray {
        val array = mutableListOf<Float>()
        freePoints.forEach {
            array.add(it.x)
            array.add(it.y)
        }
        return array.toFloatArray()
    }

    private fun parseSource() {

        var size = 0
        val width = width
        Log.i("Map", "ground.source size= " + source.size + "width = $width")
        source.forEach { byte ->
            var bit = 0
            for (i in 0 until 4) {
                when ((byte.toInt() shl bit) and 0xC0) {
                    0x40 -> {
                        val point = PointF(size % width, size / width)
                        obstaclePoints.add(point)
                        if (point.x < width && point.y < height) {
                            bitmap.setPixel(point.x.toInt(), point.y.toInt(), 0xFF4281D7.toInt())
                        }
                    }
                    0xC0 -> {
                        val point = PointF(size % width, size / width)
                        unknownPoints.add(point)
                        if (point.x < width && point.y < height) {
                            bitmap.setPixel(point.x.toInt(), point.y.toInt(), 0xFFCDCDCD.toInt())
                        }
                    }
                    else -> {
                        val point = PointF(size % width, size / width)
                        freePoints.add(point)
                        if (point.x < width && point.y < height) {
//                            bitmap.setPixel(point.x.toInt(), point.y.toInt(), 0xFFFFFFFF.toInt())
                            bitmap.setPixel(point.x.toInt(), point.y.toInt(), 0x00000000.toInt())
                        }
                    }
                }
                ++size
                bit += 2
            }
        }
    }

    override fun toString(): String {
        return "Ground(id='$id', name=$name, description=$description, width=$width, height=$height, originX=$originX, originY=$originY, zIndex=$zIndex, theta=$theta, targets=$targets, sourceSize=${source.size}, obstaclePointSize=${obstaclePoints.size}, unknownPointSize=${unknownPoints.size}, freePointSize=${freePoints.size}, bitmap=$bitmap)"
    }


}

data class Tracker(
    override val id: String,
    val line: Line,
    val type: TrackType,
    override val name: String? = null,
    override val description: String? = null,
    override val width: Float,
    override val height: Float,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val theta: Float = 0f,
    override val targets: List<String>? = null
) : Element

data class Area(
    override val id: String,
    val outline: com.ubtrobot.mapview.Path,
    val type: AreaType,
    override val name: String? = null,
    override val description: String? = null,
    override val width: Float,
    override val height: Float,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val theta: Float = 0f,
    override val targets: List<String>? = null
) : Element

data class Room(
    override val id: String,
    var outline: AndroidPath,
    override var name: String,
    val type: RoomType,
    var number: Int,
    override val description: String? = null,
    override val width: Float,
    override val height: Float,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val theta: Float = 0f,
    override val targets: List<String>? = null,
) : Element

data class Barrier(
    override val id: String,
    val outline: AndroidPath,
    val type: BarrierType,
    override val name: String? = null,
    override val description: String? = null,
    override val width: Float,
    override val height: Float,
    override val originX: Float = 0f,
    override val originY: Float = 0f,
    override val zIndex: Int = 0,
    override val theta: Float = 0f,
    override val targets: List<String>? = null,
) : Element

enum class PoseType {
    SELF,
    MAKER,
    DOCKER
}

enum class TrackType {
    VIRTUAL_WALL
}

enum class AreaType {
    FORBID,
    TASK_CLEAN
}

enum class RoomType {
    BATH,
    BED,
    DINING,
    DRAWING,
    KITCHEN,
    STUDY,
    CUSTOM
}

enum class BarrierType {
    WALL,
    SUNDRIES
}

fun Line.transform(m: Matrix): Line {
    val points = floatArrayOf(start.x, start.y, end.x, end.y)
    m.mapPoints(points)
    val start = PointF(points[0], points[1])
    val end = PointF(points[2], points[3])
    return copy(start = start, end = end)
}

fun Line.offset(dx: Float, dy: Float) {
    start.offset(dx, dy)
    end.offset(dx, dy)
}

fun Line.getDistanceToPoint(point: PointF): Float {
    // get the distance from line(pts) to point(x, y). the unit is pixel.
    // the formula comes from https://math.stackexchange.com/questions/2248617/shortest-distance-between-a-point-and-a-line-segment
    val x = point.x
    val y = point.y
    val temp = (end.y - start.y).pow(2) + (end.x - start.x).pow(2)
    val t = -((start.x - x) * (end.x - start.x) + (start.y - y) * (end.y - start.y)) / temp
    return if (t in 0.0..1.0) {
        (abs((end.y - start.y) * x - (end.x - start.x) * y + end.x * start.y - end.y * start.x) / sqrt(
            temp
        ))
    } else {
        val distanceP2 = sqrt(((end.y - y).pow(2) + (end.x - x).pow(2)))
        val distanceP1 = sqrt(((start.y - y).pow(2) + (start.x - x).pow(2)))
        if (distanceP1 < distanceP2) distanceP1 else distanceP2
    }
}

class Path : android.graphics.Path() {

    private val points = mutableSetOf<PointF>()

    override fun moveTo(x: Float, y: Float) {
        super.moveTo(x, y)
        points.add(PointF(x, y))
    }

    override fun lineTo(x: Float, y: Float) {
        super.lineTo(x, y)
        points.add(PointF(x, y))
    }

    override fun addRect(rect: RectF, dir: Direction) {
        super.addRect(rect, dir)
        points.addAll(rectToPoints(rect))
    }

    override fun reset() {
        super.reset()
        points.clear()
    }

    fun getPoints(): List<PointF> {
        return points.toList()
    }

    private fun rectToPoints(rect: RectF): List<PointF> {
        val pointLeftTop = PointF(rect.left, rect.top)
        val pointRightTop = PointF(rect.right, rect.top)
        val pointRightBottom = PointF(rect.right, rect.bottom)
        val pointLeftBottom = PointF(rect.left, rect.bottom)
        return listOf(pointLeftTop, pointRightTop, pointRightBottom, pointLeftBottom)
    }

    override fun toString(): String {
        return "Path(firstFivePoints=${if (points.size > 5) points.toList().subList(0, 5) else points},pointSize=${points.size})"
    }

}

