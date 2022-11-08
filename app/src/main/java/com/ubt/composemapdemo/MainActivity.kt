package com.ubt.composemapdemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMapDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android!!!")
                }
            }
        }
    }
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