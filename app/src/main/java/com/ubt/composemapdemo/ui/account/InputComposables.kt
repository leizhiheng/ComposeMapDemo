package com.ubt.composemapdemo.ui.account

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.ui.account.signup.EasyCountdown


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInput(modifier: Modifier = Modifier, onAccountChanged: (String) -> Unit) {
    var account by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var isShowClearedIcon by remember { mutableStateOf(false) }
    var bgColorId = if (isFocused) R.color.color_text_field_bg else R.color.white

    Box(modifier = modifier.padding(16.dp, 0.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = bgColorId)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(16.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_account_input),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                TextField(
                    value = account,
                    onValueChange = {
                        isShowClearedIcon = it.isNotEmpty()
                        account = it
                        onAccountChanged.invoke(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .onFocusChanged {
                            isFocused = when {
                                it.isFocused -> true
                                else -> false
                            }
                        },
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    placeholder = @Composable {
                        Text(
                            text = "请输入手机号码/邮箱",
                            color = colorResource(id = R.color.color_text_disable_black)
                        )
                    },
                    trailingIcon = @Composable {
                        if (isShowClearedIcon && isFocused) {
                            Image(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "clear icon",
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        account = ""
                                        onAccountChanged(account)
                                        isShowClearedIcon = false
                                    }
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Transparent,
                        containerColor = colorResource(id = bgColorId),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptchaInput(
    modifier: Modifier = Modifier,
    isSendCaptchaEnabled: Boolean = false,
    isCountDownStared: Boolean = false,
    countdownTime: Long = 0,
    onCaptchaChanged: (String) -> Unit,
    onSendCaptcha: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var focused by remember { mutableStateOf(false) }
    val bgColorId = if (focused) R.color.color_text_field_bg else R.color.white

    Box(modifier = modifier.padding(16.dp, 0.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = bgColorId)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(16.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_password_input),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        onCaptchaChanged.invoke(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true)
                        .background(color = Color.Transparent)
                        .onFocusChanged {
                            focused = when {
                                it.isFocused -> true
                                else -> false
                            }
                        },
                    placeholder = @Composable {
                        Text(
                            text = "请输入验证码",
                            color = colorResource(id = R.color.color_text_disable_black)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Transparent,
                        containerColor = colorResource(id = bgColorId),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCountDownStared) {
                        EasyCountdown(count = countdownTime)
                    } else {
                        Text(
                            text = "获取验证码",
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable(enabled = isSendCaptchaEnabled, onClick = onSendCaptcha),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = colorResource(id = if (isSendCaptchaEnabled) R.color.black else R.color.color_text_disable)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(modifier: Modifier = Modifier, placeHolder: String = "", onPasswordChanged: (String) -> Unit) {
    var pw by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var isShowClearedIcon by remember { mutableStateOf(false) }
    var isSeePw by remember { mutableStateOf(false) }
    var bgColorId = if (isFocused) R.color.color_text_field_bg else R.color.white

    Box(modifier = modifier.padding(16.dp, 0.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = colorResource(id = bgColorId)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(16.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_lock),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )

                TextField(
                    value = pw,
                    onValueChange = {
                        isShowClearedIcon = it.isNotEmpty()
                        pw = it
                        onPasswordChanged.invoke(it)
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(1f, true)
                        .background(color = Color.Transparent)
                        .onFocusChanged {
                            isFocused = when {
                                it.isFocused -> true
                                else -> false
                            }
                        },
                    visualTransformation = if (isSeePw) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    placeholder = @Composable {
                        Text(
                            text = placeHolder,
                            color = colorResource(id = R.color.color_text_disable_black)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Transparent,
                        containerColor = colorResource(id = bgColorId),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )

                AnimatedVisibility(visible = isShowClearedIcon && isFocused) {
                    Image(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear icon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(5.dp)
                            .clickable(enabled = isShowClearedIcon) {
                                pw = ""
                                onPasswordChanged(pw)
                                isShowClearedIcon = false
                            }
                    )
                }

                Image(
                    painter = painterResource(id = if (isSeePw) R.drawable.icon_see_pw_yes else R.drawable.icon_see_pw_no),
                    contentDescription = "see password icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                        .clickable {
                            isSeePw = !isSeePw
                        }
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PasswordInputPreview() {
    PasswordInput(onPasswordChanged = {}, placeHolder = "请输入密码")
}