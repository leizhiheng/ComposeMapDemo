package com.ubt.composemapdemo.ui.device

import android.graphics.PointF
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.commomcomposable.dialog.DialogBoxLoading
import com.ubt.composemapdemo.ui.commomcomposable.widget.ThreeOperationChoice
import com.ubt.composemapdemo.ui.map.*
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme
import com.ubtrobot.mapview.VirtualWall
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun MapVirtualWallScreen() {

    var viewModel: MapViewModel = viewModel()
    viewModel.getWalls()
    viewModel.virtualWalls.observeAsState().value?.let {
        MapVirtualWallContent(viewModel, walls = it)
    }


}

@Composable
private fun MapVirtualWallContent(viewModel: MapViewModel, walls:MutableList<VirtualWall>) {
    ComposeMapDemoTheme {
        var isEditing by remember { mutableStateOf(false) }

        if (viewModel.isUpdateDialogOpen.value!!) {
            LaunchedEffect(key1 = viewModel.isUpdateDialogOpen) {
                delay(1000)
                viewModel.isUpdateDialogOpen.value = false
            }
        }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            ScreenGridBg(modifier = Modifier, gridItemWidth = dp2Px(10).toFloat())

            //返回
            val (button, map, opt) = createRefs()

            //地图图层
            MapCanvasLayer(modifier = Modifier
                .size(400.dp)
                .background(Color.Blue)
                .constrainAs(map) {
                    linkTo(
                        parent.start,
                        parent.bottom,
                        parent.end,
                        parent.top
                    )
                })

            //虚拟墙图层
            val callback: (Int) -> Unit = { i: Int ->
                walls[i].state = VirtualWallState.Editing
                isEditing = true
            }

            VirtualWallCanvas(walls = walls, true, selectCallback = callback)

            //返回按钮
            BackButton(modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })

            //操作按钮
            ThreeOperationChoice(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 2.dp)
                    .height(120.dp)
                    .constrainAs(opt) {
                        linkTo(start = parent.start, end = parent.end, 10.dp, 10.dp)
                        bottom.linkTo(parent.bottom, 20.dp)
                    },
                iconIdLeft = R.drawable.ic_edit_confirm,
                iconIdMid = R.drawable.ic_add,
                iconIdRight = R.drawable.ic_edit_cancel,
                strLeft = "确认",
                strRight = "删除",
                onLeftClick = {
                    viewModel.isUpdateDialogOpen.value = true
                    isEditing = false
                    walls.filter {
                        it.state == VirtualWallState.Editing
                    }.forEach {
                        it.state = VirtualWallState.Idle
                    }

                    viewModel.insetWalls(walls)
                },
                onMidClick = {
                    isEditing = true
//                    backEnabled = true
                    walls.add(VirtualWall.getOriginalVirtualWall())
                },
                onRightClick = {
                    isEditing = false
                    walls.filter { it.state == VirtualWallState.Editing }
                        .forEach {
                            walls.remove(it)
                            viewModel.deleteWall(it)
                        }
                },
                midEnable = !isEditing,
                leftEnable = isEditing,
                rightEnable = isEditing
            )
        }

        DialogBoxLoading(dialogState = viewModel.isUpdateDialogOpen.value?: false)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VirtualWallCanvas(walls: MutableList<VirtualWall>, enable: Boolean, selectCallback: ((Int) -> Unit)? = null) {
    Box(modifier = Modifier.fillMaxSize()) {
        var editingWallIndex by remember { mutableStateOf(0) }
        var editingWall: VirtualWall? = null
        var downX by remember {
            mutableStateOf(0f)
        }
        var downY by remember {
            mutableStateOf(0f)
        }

        var downTime: Long = 0
        var upTime: Long

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                if (!enable) return@pointerInteropFilter false
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downTime = System.currentTimeMillis()
                        downX = event.x
                        downY = event.y
                        editingWallIndex = findTargetWall(walls, event, true)
                        editingWall = if (editingWallIndex > -1) {
                            walls[editingWallIndex]
                        } else {
                            null
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val disX = event.x - downX
                        val disY = event.y - downY
                        editingWall?.let {
                            if (it.draggingPoint == null) {
                                it.left += disX
                                it.right += disX
                                it.top += disY
                                it.bottom += disY
                            } else {
                                it.updateDraggingRect(disX, disY)
                            }
                        }

                        downX = event.x
                        downY = event.y
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        upTime = System.currentTimeMillis()
                        if (selectCallback != null && editingWall == null && upTime > downTime && upTime - downTime < 200 && abs(
                                downX - event.x
                            ) <= dp2Px(3) && abs(downY - event.y) <= dp2Px(3)
                        ) {
                            val touchIndex = findTargetWall(walls, event, false)
                            if (touchIndex > -1) {
                                selectCallback(touchIndex)
                            }
                        }
                        editingWallIndex = -1
                        editingWall?.reset()
                    }
                }
                true
            }, onDraw = {
            walls.forEach {
                //在这里使用一下这个state，触发recompose
                downX
                drawWall(this, it)
            }
        })
    }
}

fun findTargetWall(walls: List<VirtualWall>, event: MotionEvent, findEditingWall: Boolean): Int {
    if (findEditingWall) {
        //如果
        walls.forEachIndexed { i, wall ->
            if (wall.state == VirtualWallState.Editing && wall.left < event.x && wall.right > event.x && wall.top < event.y && wall.bottom > event.y) {
                val touchP = PointF(event.x, event.y)

                wall.points.forEachIndexed { index, pointF ->
                    if (checkDistance(pointF, touchP, dp2Px(25).toFloat())) {
                        wall.draggingPoint = pointF
                        wall.draggingPointIndex = index
                    }
                }
                return i
            }
        }
    } else {
        if (walls.filter { it.state == VirtualWallState.Editing }.isNotEmpty()) return -1
        for (i in walls.size - 1 downTo 0) {
            val wall = walls[i]
            if (wall.left < event.x && wall.right > event.x && wall.top < event.y && wall.bottom > event.y) {
                return i
            }
        }
    }
    return -1
}

fun checkDistance(p1: PointF, p2: PointF, targetDistance: Float): Boolean {
    return getDistanceOfPoints(p1, p2) <= targetDistance
}

fun getDistanceOfPoints(p1: PointF, p2: PointF): Long {
    return sqrt((p1.x - p2.x).toDouble().pow(2.0) + (p1.y - p2.y).toDouble()
        .pow(2.0)).toLong()
}

fun drawWall(scope: DrawScope, wall: VirtualWall) {
    scope.apply {
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

@Preview
@Composable
fun PreviewMapVirtualWallScreen() {
    ComposeMapDemoTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
        ) {
            MapVirtualWallScreen()
        }
    }
}