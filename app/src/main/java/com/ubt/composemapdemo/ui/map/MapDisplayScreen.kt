package com.ubt.composemapdemo.ui.map

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MapDisplayScreen() {
    Text(text = "Hello Map display")
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewMapDisplayScreen() {
    MapDisplayScreen()
}