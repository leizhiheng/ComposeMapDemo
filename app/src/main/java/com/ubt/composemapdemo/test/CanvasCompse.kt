package com.ubt.composemapdemo.test

import android.graphics.PointF
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.map.*
import kotlin.math.pow

@Composable
fun MapTest() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawPath(cRoomOutline, Color.Gray, 1.0f, Stroke())
            drawPath(cRoomWall, Color.Black, 1.0f, Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 10.dp.toPx()
            )))
            drawPath(dRoomOutline, Color.Yellow, 1.0f, Stroke())
            drawPath(dRoomWall, Color.Black, 1.0f, Stroke())
            drawPath(eRoomOutline, Color.Cyan, 1.0f, Stroke())
            drawPath(eRoomWall, Color.Red, 1.0f, Stroke())
            drawPath(eBarrier, Color.Magenta, 1.0f, Stroke())
            drawPath(fRoomOutline, Color.Red, 1.0f, Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(3.dp.toPx(), 2.dp.toPx()), 10.dp.toPx()
            )))
            drawPath(hRoomOutline, Color.Blue, 1.0f, Stroke())
            drawPath(hRoomWall, Color.Black, 1.0f, Stroke())

        })
    }
}
