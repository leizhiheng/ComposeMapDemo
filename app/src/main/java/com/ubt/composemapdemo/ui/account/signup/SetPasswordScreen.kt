package com.ubt.composemapdemo.ui.account.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.ui.account.PasswordInput
import com.ubt.composemapdemo.ui.account.wideSolidButton


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetPasswordScreen(
    onBack: () -> Unit,
    onNextStep: () -> Unit,
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
        val passwordFirst by viewModel.passwordFirst.collectAsState()
        val passwordSecond by viewModel.passwordSecond.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val isPasswordSame by viewModel.isPasswordSame.collectAsState()

        val (ivBack, title, passwordFirstLayout, passwordSecondLayout, tip, nextButton, bottomImage) = createRefs()

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

        PasswordInput(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .constrainAs(passwordFirstLayout) {
                start.linkTo(parent.start)
                top.linkTo(title.bottom, 30.dp)
                end.linkTo(parent.end)
            },
            placeHolder = "请输入密码",
            onPasswordChanged = {
                viewModel.updatePasswordFirst(it)
            })

        PasswordInput(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(passwordSecondLayout) {
                    start.linkTo(parent.start)
                    top.linkTo(passwordFirstLayout.bottom, 20.dp)
                    end.linkTo(parent.end)
                },
            placeHolder = "请再次输入密码",
            onPasswordChanged = {
                viewModel.updatePasswordSecond(it)
            })

        Text(
            text = if (isPasswordSame) "密码由6-14位数字、字母、特殊字符组合" else "密码错误，请输入6-14位数字、字母、特殊字符的组合",
            fontSize = 14.sp,
            color = if (isPasswordSame) Color.Black else Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
                .constrainAs(tip) {
                start.linkTo(parent.start)
                top.linkTo(passwordSecondLayout.bottom, 16.dp)
                end.linkTo(parent.end)
            })

        wideSolidButton(
            text = "完成",
            modifier = Modifier
                .width(220.dp)
                .height(44.dp)
                .constrainAs(nextButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tip.bottom, 40.dp)
                },
            enabled = passwordFirst.isNotEmpty() && passwordSecond.isNotEmpty(),
            onClick = {
                keyboard?.hide()
                onNextStep.invoke()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SetPasswordScreenPreview() {
    SetPasswordScreen(
        onBack = {},
        onNextStep = {})
//    AccountInput() {
//
//    }
}