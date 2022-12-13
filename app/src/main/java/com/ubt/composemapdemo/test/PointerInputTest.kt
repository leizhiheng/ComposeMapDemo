package com.ubt.composemapdemo.test

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.translationMatrix
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.showToast
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme
import kotlin.math.roundToInt

@Composable
fun PointerInputTestScreen() {
    ComposeMapDemoTheme {
        PointerInputCompose()
    }
}

@Composable
private fun PointerInputCompose() {

    val density = LocalDensity.current
    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }

    var scale by remember {
        mutableStateOf(1f)
    }

    var matrix by remember {
        mutableStateOf(Matrix())
    }

    var matrixInvert by remember {
        mutableStateOf(Matrix())
    }

    var showEditingLayer by remember { mutableStateOf(true) }
    var color by remember {
        mutableStateOf(Color.Blue)
    }
    var path by remember { mutableStateOf(Path().apply {
        this.moveTo(dp2Px(100.dp, density), dp2Px(100.dp, density))
        this.lineTo(dp2Px(200.dp, density), dp2Px(100.dp, density))
        this.lineTo(dp2Px(200.dp, density), dp2Px(200.dp, density))
        this.lineTo(dp2Px(100.dp, density), dp2Px(200.dp, density))
        this.close()
    }) }
    var pathTemp = Path()

    var paint: Paint by remember {
        mutableStateOf(Paint().apply {
            this.color = Color.Magenta
            style = PaintingStyle.Fill
        })
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.LightGray)
//            .size(300.dp)
//            .scale(scale)
//            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .padding(10.dp)
            .onSizeChanged {
                Log.d("compose_study", "box onSizeChanged size: $it")
            }
    ) {
        Canvas(modifier = Modifier
//            .background(color = color)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    Log.d("compose_study", "child detectTapGestures")
//                    color = if (color == Color.Blue) {
//                        Color.Yellow
//                    } else {
//                        Color.Blue
//                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                    matrix.translate(dragAmount.x, dragAmount.y, 0f)
                    Log.d("compose_study", "child detectDragGestures, change position: ${change.position}, dragAmount: $dragAmount")
                }
            }
            .transformable(rememberTransformableState(onTransformation = { zoomChange, panChange, rotationChange ->
                Log.d(
                    "compose_study",
                    "child transformable, zoomChange = $zoomChange, panChange = $panChange, rotation = $rotationChange"
                )
                //在这里处理移动，经过移动操作后会出现检测不到transformable事件的情况
//                offsetX += panChange.x
//                offsetY += panChange.y
                scale *= zoomChange
                matrix.scale(zoomChange, zoomChange)
            }))
            .size(200.dp)
        ) {
            offsetX
            scale

            withTransform({
//                inset(size.width/2f, size.height/2)
                this.transform(matrix)
//                translate(offsetX, offsetY)
//                scale(scale)
            }) {
                drawPath(path, Color.Blue)
            }


            drawCircle(Color.Red, 50.dp.toPx(), Offset(100.dp.toPx(), 100.dp.toPx()))
//            drawIntoCanvas { canvas ->
//                offsetX
//                Log.d("zhiheng", "draw matrix, scale = $scale")
//                canvas.save()
////                canvas.concat(matrix)
//                canvas.translate(100.dp.toPx(), 100.dp.toPx())
//                canvas.drawPath(path, paint)
//                canvas.restore()
//
//
////                this.withTransform(transformBlock = DrawTransform.transform(matrix)) {
////
////                }
//
//                canvas.save()
//                canvas.drawCircle(Offset(100.dp.toPx(), 100.dp.toPx()), 40.dp.toPx(), paint)
//                canvas.restore()
//            }

        }

//        Canvas(modifier = Modifier
//            .background(Color.Yellow)
//            .size(200.dp)
//            .fillMaxSize()) {
//
//        }
    }
}

@Preview
@Composable
fun PreviewPointerInputCompose() {
    ComposeMapDemoTheme {
        PointerInputCompose()
    }
}