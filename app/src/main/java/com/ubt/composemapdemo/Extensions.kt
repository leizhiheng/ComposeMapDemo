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