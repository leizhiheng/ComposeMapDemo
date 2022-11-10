package com.ubt.composemapdemo.ui.commomcomposable.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ubt.composemapdemo.showToast


@Composable
fun ThreeOperationChoice(
    modifier: Modifier,
    iconIdLeft: Int,
    iconIdMid: Int,
    iconIdRight: Int,
    iconMidWidth: Dp = 75.dp,
    iconSideWidth: Dp = 55.dp,
    strLeft: String,
    strRight: String,
    onLeftClick: () -> Unit = {},
    onMidClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    leftEnable: Boolean = true,
    midEnable: Boolean = true,
    rightEnable: Boolean = false
) {
    Surface(
        modifier.fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        ConstraintLayout(
            modifier = modifier
        ) {

            val (ivMode, tvMode, ivWork, ivBackStation, tvBackStation) = createRefs()
            Image(
                painter = painterResource(id = iconIdMid),
                contentDescription = "左键",
                modifier = Modifier
                    .size(iconMidWidth)
                    .alpha(if (midEnable) 1.0f else 0.3f)
                    .clickable(
                        enabled = midEnable,
                        onClick = { onMidClick.invoke() },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    .constrainAs(ivWork) {
                        linkTo(
                            start = parent.start,
                            top = parent.top,
                            end = parent.end,
                            bottom = parent.bottom
                        )
                    })

            Image(
                painter = painterResource(id = iconIdLeft),
                contentDescription = "中间",
                modifier = Modifier
                    .alpha(if (leftEnable) 1.0f else 0.3f)
                    .clickable(
                        enabled = leftEnable,
                        onClick = { onLeftClick.invoke() },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    .size(iconSideWidth)
                    .constrainAs(ivMode) {
                        top.linkTo(ivWork.top)
                        start.linkTo(parent.start, 20.dp)
                    })

            Image(
                painter = painterResource(id = iconIdRight),
                contentDescription = "右键",
                modifier = Modifier
                    .alpha(if (rightEnable) 1.0f else 0.3f)
                    .clickable(
                        enabled = rightEnable,
                        onClick = { onRightClick.invoke() },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })

                    .size(iconSideWidth)
                    .alpha(if (rightEnable) 1.0f else 0.3f)
                    .constrainAs(ivBackStation) {
                        top.linkTo(ivWork.top)
                        end.linkTo(parent.end, 20.dp)
                    })

            Text(
                text = strLeft,
                fontSize = 12.sp,
                modifier = Modifier
                    .alpha(if (leftEnable) 1.0f else 0.3f)
                    .constrainAs(tvMode) {
                        top.linkTo(ivMode.bottom, 5.dp)
                        linkTo(start = ivMode.start, end = ivMode.end)
                    })

            Text(
                text = strRight,
                fontSize = 12.sp,
                modifier = Modifier
                    .alpha(if (rightEnable) 1.0f else 0.3f)
                    .constrainAs(tvBackStation) {
                        top.linkTo(ivBackStation.bottom, 5.dp)
                        linkTo(start = ivBackStation.start, end = ivBackStation.end)
                    })
        }
    }
}