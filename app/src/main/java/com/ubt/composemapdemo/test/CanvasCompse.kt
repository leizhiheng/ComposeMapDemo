package com.ubt.composemapdemo.test

import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Region
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.device.checkDistance
import com.ubt.composemapdemo.ui.device.drawWall
import com.ubt.composemapdemo.ui.map.*
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme
import com.ubtrobot.mapview.DragPosition
import com.ubtrobot.mapview.VirtualWall
import kotlin.math.roundToInt


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapTest() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        //地图数据
        val robotMap by remember { mutableStateOf(FakerData.create()) }
        val mapAspect = robotMap.width / robotMap.height
        var mapScale by remember { mutableStateOf(1f) }
        val mapWidthDp = with(LocalDensity.current) {
            robotMap.width.toDp()
        }
        val mapHeightDp = with(LocalDensity.current) {
            robotMap.height.toDp()
        }
        var boxWidth by remember { mutableStateOf(mapWidthDp) }
        var boxHeight by remember { mutableStateOf(mapHeightDp) }

        //        val squareSize = 40.dp
//        val sizePx = with(LocalDensity.current) {
//            squareSize.toPx()
//        }

        var scale by remember { mutableStateOf(1f) }
        var rotation by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
        }

        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        var subOffsetX by remember { mutableStateOf(0f) }
        var subOffsetY by remember { mutableStateOf(0f) }

        var editingWall by remember { mutableStateOf(VirtualWall.getOriginalVirtualWall()) }
        var editingWallIndex by remember { mutableStateOf(0) }
        var downX by remember {
            mutableStateOf(0f)
        }
        var downY by remember {
            mutableStateOf(0f)
        }

        var downTime by remember { mutableStateOf(0L) }
        var dragPosition: DragPosition by remember { mutableStateOf(DragPosition.OutRect) }

        var dragOffset by remember { mutableStateOf(Offset.Zero) }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .width(boxWidth)
            .aspectRatio(mapAspect, false)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .scale(scale)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale *= zoom
                    editingWall.let {
                        when {
                            centroid.x < it.left -> {

                            }
                            centroid.y > it.right -> {

                            }
                            else -> {
                                it.left = it.left / zoom
                                it.right = it.right / zoom
                                it.top = it.top / zoom
                                it.bottom = it.bottom / zoom
                            }
                        }
                    }
                    Log.d("zhiheng", "centroid: $centroid, transform: $pan, zoom: $zoom, scale: $scale")
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    Log.d("zhiheng", "onTap: $it")
                    downTime = System.currentTimeMillis()
                    if (editingWall.state == VirtualWallState.Idle) {
                        editingWall.state = VirtualWallState.Editing
                    }
                }
            }
            .pointerInput(Unit) {
                //触摸区域随着拖动而改变
                detectDragGestures(onDragStart = {
                    dragPosition = parseDragPosition(editingWall, it)
                }, onDragEnd = {
                    dragPosition = DragPosition.OutRect
                }, onDragCancel = {
                    dragPosition = DragPosition.OutRect
                }, onDrag = { change, dragAmount ->

                    Log.d("zhiheng", "onDrag: $dragAmount")
                    when (dragPosition) {
                        DragPosition.InRect -> {
                            offsetWall(editingWall, dragAmount)
                        }
                        DragPosition.InDragCorner -> {
                            editingWall.updateDraggingRect(dragAmount.x, dragAmount.y)
                        }
                        else -> {
                            editingWall.let {
                                it.left -= (dragAmount.x / scale)
                                it.right -= (dragAmount.x / scale)
                                it.top -= (dragAmount.y / scale)
                                it.bottom -= (dragAmount.y / scale)
                            }
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
                    downTime = System.currentTimeMillis()
                })
            }
            .background(Color.LightGray), onDraw = {
            downTime
            mapScale = size.width / robotMap.width
            scale(mapScale, mapScale) {
                //绘制房间图层
                drawMapLayer(this)
                val scaleReverse = 1 / scale//缩放时保持原来大小
                drawEditingWall(this, editingWall)
                scale(scaleReverse, scaleReverse) {
                }

                drawIntoCanvas {

                }
            }
        })
    }
}


fun drawEditingWall(scope: DrawScope, wall: VirtualWall) {
    scope.apply {
        Log.d("zhiheng", "drawEditingWall wall:${wall}")
        //绘制方框
        drawRect(
            Color.Red,
            Offset(wall.left, wall.top),
            Size(wall.getWidth(), wall.getHeight()),
            1.0f,
            Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(4.dp.toPx(), 4.dp.toPx()),
                    4.dp.toPx()
                )
            )
        )

        //绘制方框
        drawRect(
            Color.Red,
            Offset(wall.left, wall.top),
            Size(wall.getWidth(), wall.getHeight()),
            0.3f
        )

        if (wall.state is VirtualWallState.Editing) {
            drawCircle(Color.Red, 20.dp.toPx(), Offset(wall.left, wall.top))
            drawCircle(Color.Red, 20.dp.toPx(), Offset(wall.right, wall.top))
            drawCircle(Color.Red, 20.dp.toPx(), Offset(wall.left, wall.bottom))
            drawCircle(Color.Red, 20.dp.toPx(), Offset(wall.right, wall.bottom))
        }
    }
}


fun offsetWall(wall: VirtualWall?, dragAmount: Offset) {
    wall?.let {
        it.left += dragAmount.x
        it.right += dragAmount.x
        it.top += dragAmount.y
        it.bottom += dragAmount.y
    }
}

fun parseDragPosition(wall: VirtualWall?, position: Offset): DragPosition {
    var p: DragPosition = DragPosition.OutRect
    val touchP = PointF(position.x, position.y)
    wall?.let {
        if (wall.state == VirtualWallState.Editing && it.left < position.x && it.right > position.x && it.top < position.y && it.bottom > position.y) {
            p = DragPosition.InRect
            wall.points.forEachIndexed { index, pointF ->
                if (checkDistance(pointF, touchP, dp2Px(25).toFloat())) {
                    wall.draggingPoint = pointF
                    wall.draggingPointIndex = index
                    p = DragPosition.InDragCorner
                }
            }
        }
    }
    return p
}

fun drawMapLayer(scope: DrawScope) {
    scope.apply {
        drawPath(
            cRoomWall, Color.Black, 1.0f, Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 10.dp.toPx()
                )
            )
        )
        drawPath(dRoomOutline, Color.Yellow, 1.0f, Stroke())
        drawPath(dRoomWall, Color.Black, 1.0f, Stroke())
        drawPath(eRoomOutline, Color.Cyan, 1.0f, Stroke())
        drawPath(eRoomWall, Color.Red, 1.0f, Stroke())
        drawPath(fRoomWall, Color.Magenta, 1.0f, Stroke())
        drawPath(
            fRoomOutline, Color.Red, 1.0f, Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(3.dp.toPx(), 2.dp.toPx()), 10.dp.toPx()
                )
            )
        )
        drawPath(hRoomOutline, Color.Blue, 1.0f, Stroke())
        drawPath(hRoomWall, Color.Black, 1.0f, Stroke())
    }
}

fun isInPath(path: android.graphics.Path, x: Float, y: Float): Boolean {
    val rectF = RectF()
    path.computeBounds(rectF, true)
    val region = Region()
    region.setPath(
        path,
        Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
    )
    Log.d("zhiheng", "isInPath rectF: ${rectF}")
    return region.contains(x.toInt(), y.toInt())
}

@Preview
@Composable
fun PreviewMapTest() {
    ComposeMapDemoTheme {
        MapTest()
    }
}
