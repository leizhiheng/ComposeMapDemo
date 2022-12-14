package com.ubt.composemapdemo.ui.account.signin

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

//data class SignUpInputUiState(
//    val account: String = "",
//    val captcha: String = "",
//    val isCaptchaSent: Boolean = false,
//    val isLoading: Boolean = false
//)

class SignInViewModel: ViewModel() {

    private val _account = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)

    val account: StateFlow<String> = _account
    val password: StateFlow<String> = _password
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getLastAccount()
    }

    fun signIn() {

    }

    fun updateAccount(newAccount: String) {
        _account.value = newAccount
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    private fun isLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private fun getLastAccount() {
        _account.value = ""
    }
}