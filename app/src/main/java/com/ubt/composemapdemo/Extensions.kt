package com.ubt.composemapdemo

import android.widget.Toast
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun showToast(message: String) {
    Toast.makeText(App.getApp(), message, Toast.LENGTH_SHORT).show()
}


fun showToast(resId: Int) {
    App.getApp().apply {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show()
    }
}

fun dp2Px(dp: Int) : Int {
    return App.getApp().resources?.displayMetrics?.density?.let {
         (dp *  it + 0.5).toInt()
    }?: dp
}
fun dp2Px(dp: Float) : Float {
    return App.getApp().resources?.displayMetrics?.density?.let {
        (dp *  it + 0.5f)
    }?: dp
}

fun dp2Px(dp: Dp, density: Density) : Float {

    with(density) {
       return dp.toPx()
    }
}

fun px2Dp(px: Int, density: Density): Dp {
    with(density) {
        return px.toDp()
    }
}

const val TAG = "leizhiheng"