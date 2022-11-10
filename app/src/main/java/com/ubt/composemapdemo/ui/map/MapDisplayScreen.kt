package com.ubt.composemapdemo.ui.map

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.device.VirtualWallCanvas
import com.ubt.composemapdemo.ui.navigation.Router
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme
import kotlin.math.roundToInt

@Composable
fun MapDisplayScreen(viewModel: MapViewModel) {
    ComposeMapDemoTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            ScreenGridBg(modifier = Modifier, gridItemWidth = dp2Px(10).toFloat())

            val (button, map, opt) = createRefs()

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
            
            VirtualWallCanvas(walls = viewModel.virtualWalls, viewModel.canvasEnable)

            BackButton(modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
            
            MapOperateItems(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(20.dp)
                .constrainAs(opt) {
                    linkTo(start = parent.start, end = parent.end)
                    bottom.linkTo(parent.bottom, 20.dp)

                }) {
                Router.toMapVirtualWallScreen()
            }
        }
    }
}

@Composable
fun MapOperateItems(modifier: Modifier, onVirtualClick: () -> Unit) {
    Surface(modifier = modifier, shape = RoundedCornerShape(10.dp), color = Color.White, shadowElevation = 3.dp) {
        Column(modifier = Modifier.padding(10.dp, 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_add_virtual_line),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onVirtualClick.invoke() }
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "虚拟墙",
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun MapCanvasLayer(modifier: Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            drawRect(Color.DarkGray, Offset(0f, 0f), Size(size.width, size.height))
            drawPath(cRoomOutline, Color.Gray, 1.0f, Stroke())
            drawPath(
                cRoomWall, Color.Black, 1.0f, Stroke(
                    width = 2.dp.toPx(), cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 10.dp.toPx()
                    )
                )
            )
            drawPath(dRoomOutline, Color.Yellow, 1.0f, Stroke())
            drawPath(dRoomWall, Color.Black, 1.0f, Stroke())
            drawPath(eRoomOutline, Color.Cyan, 1.0f, Stroke())
            drawPath(eRoomWall, Color.Red, 1.0f, Stroke())
            drawPath(eBarrier, Color.Magenta, 1.0f, Stroke())
            drawPath(
                fRoomOutline, Color.Red, 1.0f, Stroke(
                    width = 2.dp.toPx(), cap = StrokeCap.Round, pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(3.dp.toPx(), 2.dp.toPx()), 10.dp.toPx()
                    )
                )
            )
            drawPath(hRoomOutline, Color.Blue, 1.0f, Stroke())
            drawPath(hRoomWall, Color.Black, 1.0f, Stroke())
        })
    }
}

@Composable
fun BackButton(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { Router.popBack() },
        border = null,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(12.dp, 2.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back_left),
            contentDescription = "",
            tint = Color.Blue
        )
        Text(text = "Back", fontSize = 18.sp, color = Color.Blue)
    }
}

@Composable
fun ScreenGridBg(modifier: Modifier, gridItemWidth: Float) {
    Canvas(modifier = modifier.fillMaxSize(), onDraw = {
        var width = size.width
        var height = size.height
        //绘制竖线
        for (i in 0..((width / gridItemWidth).roundToInt())) {
            drawLine(
                Color.LightGray,
                Offset(i * gridItemWidth, 0f),
                Offset(i * gridItemWidth, height),
                0.5f
            )
        }
        //绘制横线
        for (i in 0..((height / gridItemWidth).roundToInt())) {
            drawLine(
                Color.LightGray,
                Offset(0f, i * gridItemWidth),
                Offset(width, i * gridItemWidth),
                0.5f
            )
        }
    })
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewMapDisplayScreen() {
    ComposeMapDemoTheme {
        MapDisplayScreen(viewModel(modelClass = MapViewModel::class.java))
    }
}
