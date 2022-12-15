package com.ubt.composemapdemo.test

import android.animation.ObjectAnimator
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.PI
import kotlin.math.roundToInt


class IntState(private val state: MutableState<Int>){
    var value : Int = state.value
        get() = state.value
        set(value) {
            field = value
            state.value = value
        }
}
fun mutableIntStateOf(value: Int, policy: SnapshotMutationPolicy<Int> = structuralEqualityPolicy()) : IntState{
    val state = mutableStateOf(value, policy)
    return IntState(state)
}

@Composable
fun PropertyAnimTest() {
    val topPadding = mutableIntStateOf(10)

    val animator = ObjectAnimator.ofInt(topPadding, "value", 10, 100)
    animator.duration = 1000

    Box(
        Modifier
            .padding(start = 10.dp, top = topPadding.value.dp)
            .size(100.dp)
            .background(Color.Blue)
            // 添加点击事件
            .clickable {
                // 启动动画
                animator.start()
            }
    )
}

@Composable
fun DraggableTest() {
    ComposeMapDemoTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            var offsetX by remember { mutableStateOf(0f) }
            Canvas(modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    }
                )) {
                drawCircle(Color.Red, 50.dp.toPx())
            }
        }
    }
}

fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * PI / 180
    return Offset(
        (x * cos(angleInRadians) - y * sin(angleInRadians)).toFloat(),
        (x * sin(angleInRadians) + y * cos(angleInRadians)).toFloat()
    )
}

@Composable
fun ScaleCompose(modifier: Modifier) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableStateOf(1f) }
    var angle by remember { mutableStateOf(0f) }

    Box(
        modifier
            .pointerInput(Unit) {
                detectTransformGestures(
                    onGesture = { centroid, pan, gestureZoom, gestureRotate ->
                        val oldScale = zoom
                        val newScale = zoom * gestureZoom

                        // For natural zooming and rotating, the centroid of the gesture should
                        // be the fixed point where zooming and rotating occurs.
                        // We compute where the centroid was (in the pre-transformed coordinate
                        // space), and then compute where it will be after this delta.
                        // We then compute what the new offset should be to keep the centroid
                        // visually stationary for rotating and zooming, and also apply the pan.
                        offset = (offset + centroid / oldScale).rotateBy(gestureRotate) -
                                (centroid / newScale + pan / oldScale)
                        zoom = newScale
                        angle += gestureRotate
                    }
                )
            }
            .graphicsLayer {
                translationX = -offset.x * zoom
                translationY = -offset.y * zoom
                scaleX = zoom
                scaleY = zoom
                rotationZ = angle
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .background(Color.Blue)
            .size(300.dp)
    )
}

@Composable
fun JoinButton(onClick: (Boolean) -> Unit = {}) {
    var buttonState: JoinButtonState
            by remember { mutableStateOf(JoinButtonState.IDLE) }

// Button shape
    val shape = RoundedCornerShape(corner = CornerSize(12.dp))

// Button background
    val buttonBackgroundColor: Color =
        if (buttonState == JoinButtonState.PRESSED)
            Color.White
        else
            Color.Blue

// Button icon
    val iconAsset: ImageVector =
        if (buttonState == JoinButtonState.PRESSED)
            Icons.Default.Check
        else
            Icons.Default.Add
    val iconTintColor: Color =
        if (buttonState == JoinButtonState.PRESSED)
            Color.Blue
        else
            Color.White


    Box(
        modifier = Modifier
            .clip(shape)
            .border(width = 1.dp, color = Color.Blue, shape = shape)
            .background(color = buttonBackgroundColor)
            .size(width = 80.dp, height = 50.dp)
            .clickable(onClick = {
                buttonState =
                    if (buttonState == JoinButtonState.IDLE) {
                        onClick.invoke(true)
                        JoinButtonState.PRESSED
                    } else {
                        onClick.invoke(false)
                        JoinButtonState.IDLE
                    }
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconAsset,
            contentDescription = "Plus Icon",
            tint = iconTintColor,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append("Click ")

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(tag = "URL",
            annotation = "https://developer.android.com")
        withStyle(style = SpanStyle(color = Color.Blue,
            fontWeight = FontWeight.Bold)
        ) {
            append("here")
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(tag = "URL", start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                }
        }
    )
}

@Preview
@Composable
fun AnnotatedClickableTextPreviww() {
    AnnotatedClickableText()
}

enum class JoinButtonState {
    IDLE,
    PRESSED
}

//@Preview
//@Composable
//fun JoinButtonPreview() {
//    JoinButton(onClick = {})
//}
//
//@Preview
//@Composable
//fun PreviewDraggableTest() {
//    ScaleCompose(modifier = Modifier)
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun MyCanvas() {
//    //记录初始按下的点
//    var downX by remember {
//        mutableStateOf(0f)
//    }
//    var downY by remember {
//        mutableStateOf(0f)
//    }
//    //绘制路径
//    val path = Path()
//
//    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.BottomCenter) {
//        Canvas(modifier = Modifier
//            .fillMaxSize()
//            //使用pointerInteropFilter来获取触摸事件
//            .pointerInteropFilter { event ->
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        //按下屏幕时，path移动到点击的地方
//                        downX = event.x
//                        downY = event.y
//                        path.moveTo(downX, downY)
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        //手指移动时，绘制线条，并重置downX和downY
//                        path.lineTo(downX, downY)
//                        downX = event.x
//                        downY = event.y
//                    }
//                }
//                true
//            }, onDraw = {
//            //在这里使用一下这个变量，当downX发生变化时，进行recompose
//            downX
//            //把记录的path绘制出来
//            drawPath(
//                path,
//                color = Color.Red,
//                style = Stroke(width = 20f, cap = StrokeCap.Round, join = StrokeJoin.Round)
//            )
//        })
//
//        //给画板添加清除的功能
//        //在button的onClick事件中，调用path.reset()，再将downX置为0触发Canvas的recompose即可
//        Button(onClick = {
//            path.reset()
//            downX = 0f
//        }) {
//            Text(text = "清除全部")
//        }
//    }
//}