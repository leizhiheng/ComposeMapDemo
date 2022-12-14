package com.ubt.composemapdemo.ui.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ubt.composemapdemo.R

@Composable
fun wideStrokeButton(
    modifier: Modifier,
    text: String,
    textSize: Int = 14,
    containerColorId: Int = R.color.white,
    contentColorId: Int = R.color.color_6C93A8,
    strokeWidth: Dp = 1.dp,
    strokeColorId: Int = R.color.color_6C93A8,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = containerColorId),
            contentColor = colorResource(id = contentColorId)
        ),
        border = BorderStroke(strokeWidth, colorResource(id = strokeColorId)),
        onClick = onClick
    ) {
        Text(text = text, fontSize = textSize.sp)
    }
}

@Composable
fun wideSolidButton(
    modifier: Modifier,
    text: String,
    textSize: Int = 14,
    containerColorId: Int = R.color.color_6C93A8,
    contentColorId: Int = R.color.white,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = containerColorId),
            contentColor = colorResource(id = contentColorId)
        ),
        onClick = onClick
    ) {
        Text(text = text, fontSize = textSize.sp)
    }
}


@Preview(showSystemUi = true)
@Composable
fun wideButtonPreview() {
    wideStrokeButton(
        modifier = Modifier
            .width(220.dp)
            .height(44.dp),
        text = "注册", onClick = { })
}