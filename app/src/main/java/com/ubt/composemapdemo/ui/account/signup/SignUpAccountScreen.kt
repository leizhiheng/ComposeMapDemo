package com.ubt.composemapdemo.ui.account.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.ui.account.AccountInput
import com.ubt.composemapdemo.ui.account.CaptchaInput
import com.ubt.composemapdemo.ui.account.wideSolidButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpAccountScreen(
    onBack: () -> Unit,
    onNextStep: (accountName: String, captcha: String) -> Unit,
    viewModel: SignupViewModel = viewModel()
) {
    val keyboard = LocalSoftwareKeyboardController.current

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    }

    ConstraintLayout(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures {
                keyboard?.hide()
            }
        }
        .fillMaxSize()) {
        val countdownTime by viewModel.countDownTime.collectAsState()
        val account by viewModel.account.collectAsState()
        val captcha by viewModel.captcha.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        val (ivBack, title, accountLayout, passwordLayout, nextButton, bottomImage) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_signup_bg),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(bottomImage) {
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.FillWidth,
        )

        Image(
            painter = painterResource(id = R.drawable.ic_back_arr),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(12.dp)
                .background(Color.Transparent)
                .constrainAs(ivBack) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(parent.top, 63.dp)
                }
                .indication(interactionSource = MutableInteractionSource(), indication = null)
                .clickable(onClick = {
                    keyboard?.hide()
                    onBack.invoke()
                })
        )

        Text(
            text = "新用户注册",
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(ivBack.bottom, 32.dp)
                end.linkTo(parent.end)
            })

        AccountInput(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .constrainAs(accountLayout) {
                start.linkTo(parent.start)
                top.linkTo(title.bottom, 30.dp)
                end.linkTo(parent.end)
            }, onAccountChanged = {
            viewModel.updateAccount(it)
        })

        CaptchaInput(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(passwordLayout) {
                    start.linkTo(parent.start)
                    top.linkTo(accountLayout.bottom, 20.dp)
                    end.linkTo(parent.end)
                },
            isSendCaptchaEnabled = account.isNotEmpty(),
            isCountDownStared = countdownTime > 0,
            countdownTime = countdownTime,
            onCaptchaChanged = { viewModel.updateCaptcha(it) },
            onSendCaptcha = { viewModel.sendCaptcha() })

        wideSolidButton(
            text = "下一步",
            modifier = Modifier
                .width(220.dp)
                .height(44.dp)
                .constrainAs(nextButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(passwordLayout.bottom, 30.dp)
                },
            enabled = account.isNotEmpty() && captcha.isNotEmpty(),
            onClick = {
                keyboard?.hide()
                onNextStep.invoke(account, captcha)
            }
        )
    }
}

@Composable
fun EasyCountdown(count: Long) {
    Surface(
        color = colorResource(id = R.color.color_text_gray_bg),
        modifier = Modifier
            .wrapContentWidth(), shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = colorResource(id = R.color.color_text_gray_bg))
                .sizeIn(minWidth = 37.dp, minHeight = 25.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    SignUpAccountScreen(onBack = { /*TODO*/ }, onNextStep = { account, captcha ->

    })
}