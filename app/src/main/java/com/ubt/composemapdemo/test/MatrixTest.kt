package com.ubt.composemapdemo.test

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme

@Composable
fun MatrixTest() {
    ComposeMapDemoTheme {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier
                .size(100.dp), onDraw = {
                var path = Path()
                path.moveTo(100.dp.toPx(), 100.dp.toPx())
                path.lineTo(200.dp.toPx(), 100.dp.toPx())
                path.lineTo(200.dp.toPx(), 200.dp.toPx())
                path.lineTo(100.dp.toPx(), 200.dp.toPx())
                path.close()
                drawPath(path, Color.Blue)
                var path2 = Path()
            })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMatrixTest() {
    MatrixTest()
}