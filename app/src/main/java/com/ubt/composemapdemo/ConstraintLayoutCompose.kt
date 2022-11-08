package com.ubt.composemapdemo

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1, text1, button2) = createRefs()
        createHorizontalChain(button1, text1, button2, chainStyle = ChainStyle.Packed)

        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button1) {
            linkTo(top = parent.top, bottom = parent.bottom, start = parent.start, end = text1.start, startMargin = 20.dp, endMargin = 40.dp)
        }) {
            Text(text = "Button1")
        }

        Text(text = "Text1", modifier = Modifier.constrainAs(text1) {
            linkTo(top = button1.top, bottom = button1.bottom, start = button1.end, end = button2.start)
        })

        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button2) {
            linkTo(top = button1.top, bottom = button1.bottom, start = text1.end, end = parent.end, startMargin = 20.dp, endMargin = 40.dp)
        }) {
            Text(text = "Button2")
        }
    }
}

@Preview (showSystemUi = true)
@Composable
fun PreviewConstraintLayoutContent() {
    ConstraintLayoutContent()
}


@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append("Click ")

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(tag = "URL", annotation = "https://developer.android.com")
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

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewAnnotatedText() {
    AnnotatedClickableText()
}