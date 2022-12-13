package com.ubt.composemapdemo.test

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.values
import com.ubt.composemapdemo.ui.device.drawWall
import com.ubt.composemapdemo.ui.map.MapViewModel
import com.ubt.composemapdemo.ui.map.VirtualWallState
import com.ubtrobot.mapview.VirtualWall

@Composable
fun MapMatrixComposeScreen(viewModel: MapViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    viewModel.getWalls()
    viewModel.virtualWalls.observeAsState().value?.let {
        MapMatrixCompose(viewModel, walls = it)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapMatrixCompose(viewModel: MapViewModel, walls: MutableList<VirtualWall>) {
    var robotMap = viewModel.robotMap.observeAsState()
    var mapAspect = robotMap.value!!.width / robotMap.value!!.height

    var scale by remember { mutableStateOf(1f) }
    var rotate by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var editingWall by remember { mutableStateOf(VirtualWall.getOriginalVirtualWall()) }
    var matrix by remember { mutableStateOf(android.graphics.Matrix()) }


    val path = android.graphics.Path()
    var time by remember { mutableStateOf(0L) }

    var fitCenter by remember { mutableStateOf(false) }
    var pointerCount by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .fillMaxSize()
//            .rotate(rotate)
//            .scale(scale)
//            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .zIndex(1f)
            .aspectRatio(mapAspect, false)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    when (pointerCount) {
                        1 -> {
                            matrix.postTranslate(pan.x, pan.y)
                            Log.d("zhiheng", "detectTransformGestures 1 pointer matrix: $pan")
                        }
                        2 -> {
                            scale *= zoom
                            matrix.setScale(scale, scale, centroid.x, centroid.y)
                            Log.d("zhiheng", "detectTransformGestures 2 pointer matrix: $matrix")
                        }
                        else -> {
                            Log.d("zhiheng", "detectTransformGestures unknown pointer")
                        }
                    }
                    time = System.currentTimeMillis()
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    if (editingWall.state == VirtualWallState.Idle) {
                        editingWall.state = VirtualWallState.Editing
                    }
                }
            }
            .pointerInteropFilter {
                pointerCount = it.pointerCount
                Log.d("zhiheng", "pointerCount: $pointerCount")
                false
            }
//            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//                    change.consume()
//                    offsetX += dragAmount.x
//                    offsetY += dragAmount.y
//                    matrix.preTranslate(dragAmount.x, dragAmount.y)
//                    Log.d("zhiheng", "detectDragGestures matrix: $matrix")
//                    time = System.currentTimeMillis()
//                }
//            }
        ) {

            time

            drawIntoCanvas { canvas ->
                Log.d("zhiheng", "drawIntoCanvas..matrix: $matrix")
//                canvas.translate(size.width / 2f, size.height / 2f)
                robotMap.value?.rooms?.let { rooms ->
                    rooms.forEach {
                        path.reset()
                        path.addPath(it.outline.asAndroidPath(), matrix)
                        drawPath(path.asComposePath(), Color.Blue, style = Stroke(width = 2.dp.toPx()))
                    }
                }

                drawWall(this, editingWall)
            }
        }
    }
}