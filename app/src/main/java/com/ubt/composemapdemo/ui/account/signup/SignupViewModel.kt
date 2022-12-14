package com.ubt.composemapdemo.ui.account.signup

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

class SignupViewModel: ViewModel() {
    companion object {
        const val PERIOD_TIME = 1000L
        const val RESEND_CAPTCHA_INTERVAL_SEC = 10L
    }

    private var mInitialTime: Long = 0
    private var isCountingDown = false

    private val _account = MutableStateFlow("")
    private val _captcha = MutableStateFlow("")
    private val _countdownTime = MutableStateFlow(0L)
    private val _isLoading = MutableStateFlow(false)
    private val _passwordFirst = MutableStateFlow("")
    private val _passwordSecond = MutableStateFlow("")
    private val _isPasswordSame = MutableStateFlow(true)

    val account: StateFlow<String> = _account
    val captcha: StateFlow<String> = _captcha
    val countDownTime: StateFlow<Long> = _countdownTime
    val isLoading: StateFlow<Boolean> = _isLoading
    val passwordFirst: StateFlow<String> = _passwordFirst
    val passwordSecond: StateFlow<String> = _passwordSecond
    val isPasswordSame: StateFlow<Boolean> = _isPasswordSame

    init {
        getLastAccount()
    }

    fun sendCaptcha() {
        isLoading(true)
        requestSendCaptcha()
    }

    fun updateAccount(newAccount: String) {
        _account.value = newAccount
    }

    fun updateCaptcha(newCaptcha: String) {
        _captcha.value = newCaptcha
    }

    fun updatePasswordFirst(newPassword: String) {
        _passwordFirst.value = newPassword
        checkPasswordIsSame()
    }

    fun updatePasswordSecond(newPassword: String) {
        _passwordSecond.value = newPassword
        checkPasswordIsSame()
    }

    private fun isLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private fun getLastAccount() {
        _account.value = ""
    }

    private fun requestSendCaptcha() {
        viewModelScope.launch {
            //repository request send captcha
            delay(1000)
            isLoading(false)
            startCountDown()
        }
    }

    private fun checkPasswordIsSame() {
        val pwFirst = _passwordFirst.value
        val pwSecond = _passwordSecond.value
        if (pwFirst.length >= pwSecond.length) {
            _isPasswordSame.value = pwFirst.startsWith(pwSecond)
        } else {
            _isPasswordSame.value = pwSecond.startsWith(pwFirst)
        }
    }

    private fun startCountDown(){
        if (isCountingDown) {
            return
        }

        mInitialTime = SystemClock.elapsedRealtime()
        _countdownTime.value = RESEND_CAPTCHA_INTERVAL_SEC
        isCountingDown = true

        val timer = Timer()
        val timeTask = object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                if (newValue < RESEND_CAPTCHA_INTERVAL_SEC + 1){
                    _countdownTime.value = _countdownTime.value -1
                }else{
                    timer.cancel()
                    isCountingDown = false
                }
            }
        }
        timer.scheduleAtFixedRate(timeTask, PERIOD_TIME, PERIOD_TIME)
    }
}