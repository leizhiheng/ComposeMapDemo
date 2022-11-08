package com.ubt.composemapdemo.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme

@Composable
fun MapVirtualWallScreen() {
    ComposeMapDemoTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)) {
            ConstraintLayout {
                Text(text = "Virtual Wall")
            }
        }
    }
}

@Preview
@Composable
fun PreviewMapVirtualWallScreen() {
    ComposeMapDemoTheme {
        Surface(modifier = Modifier.fillMaxSize().background(color = Color.LightGray)) {
            MapVirtualWallScreen()
        }
    }
}