package com.ubt.composemapdemo.ui.device

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class DeviceViewModel(application: Application): AndroidViewModel(application) {
    private val deviceRepository = DeviceRepository()
}