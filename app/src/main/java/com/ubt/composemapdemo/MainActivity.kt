package com.ubt.composemapdemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ubt.composemapdemo.ui.account.nav.AccountNavGraph
import com.ubt.composemapdemo.ui.account.signin.SignInScreen
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            ComposeMapDemoTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = Color.Gray
//                ) {
//                    Navigator()
//                }
//                var viewModel: MapViewModel = viewModel()
//                CanvasTest(viewModel.virtualWalls)
//                MapMatrixComposeScreen()

                AccountNavGraph()
//                DialogBoxLoading()
            }
        }

//        val animRect = mutableStateOf( Rect(0, 0, 100, 100)).value
//        // 使用 mutableStateOf 创建 topPadding 的 State
//        var topPadding by mutableStateOf(10)
//        // 创建 ValueAnimator 从 10 变化到 100
//        val animator = ValueAnimator.ofInt(10, 100)
//        // 动画时长 1s
//        animator.duration = 1000
//        // 设置监听，当动画改变时动态修改 topPadding 的值
//        animator.addUpdateListener {
//            topPadding = it.animatedValue as Int
//            animRect.apply {
//                right += topPadding
//                bottom += topPadding
//            }
//        }
//
//        setContent {
//
//            Box(
//                Modifier
////                    .padding(start = 10.dp, top = topPadding.dp)
//                    .height(100.dp + topPadding.dp)
//                    .width(100.dp + topPadding.dp)
//                    .background(Color.Blue)
//                    .clickable {
//                        animator.start()
//                    }
//            ) {
//
//                Canvas(modifier = Modifier.fillMaxSize()) {
//                    Log.d("zhiheng", "Box widht is ${(100.dp.toPx() + topPadding.dp.toPx())}")
//                    Log.d("zhiheng", "Canvas size.with = ${size.width}, height = ${size.height}")
//                    var r = size.width/2
//                    drawCircle(Color.Red, r, Offset(r, r))
//                }
//            }
//        }
    }
}

@Composable
fun CircleImageView() {
    Image(
        painter = painterResource(R.drawable.ic_back_left),
        contentDescription = "Circle Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape) // clip to the circle shape
            .border(5.dp, Color.Gray, CircleShape)//optional
    )
}



fun Modifier.firstBaseLineToTop(firstBaselineToTop: Dp) = layout { measurable, constraints ->
// Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }

}


@Composable
fun Greeting(name: String) {
    var resources = painterResource(id = R.drawable.ic_launcher_foreground)

    Text("Hi there!", Modifier.firstBaseLineToTop(80.dp))

//    Column {
//        Text(text = "Hello $name!")
//        var state  by remember { mutableStateOf(false) }
//        Button(onClick = { state = !state }) {
//            // Inner content including an icon and a text label
//            Row {
//                Icon(
//                    painterResource(id = R.drawable.ic_launcher_background),
//                    contentDescription = "Favorite",
//                    modifier = Modifier.size(ButtonDefaults.IconSize)
//                )
//                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                Text("Like")
//            }
//        }
//    }

}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview() {
    ComposeMapDemoTheme {
        Greeting("Android")
    }
}