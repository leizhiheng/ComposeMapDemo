package com.ubt.composemapdemo

import android.widget.Toast

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

fun px2Dp(px: Int): Int {
    return App.getApp().resources?.displayMetrics?.density?.let {
        (px.toDouble()/it + 0.5).toInt()
    }?: px
}

const val TAG = "leizhiheng"