package com.ubt.composemapdemo.ui.account.display

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DisplayViewModel: ViewModel() {
    private var _isAgreedPolicy = MutableStateFlow(false)
    val isAgreedPolicy: StateFlow<Boolean> = _isAgreedPolicy

    fun updateAgreementState() {
        _isAgreedPolicy.value = !_isAgreedPolicy.value
    }
}