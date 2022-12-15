package com.ubt.composemapdemo.ui.account.signin

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.ui.account.AccountInput
import com.ubt.composemapdemo.ui.account.PasswordInput
import com.ubt.composemapdemo.ui.account.wideSolidButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(
    onBack: () -> Unit,
    onSignUpClicked: () -> Unit,
    onForgetPasswordClicked: () -> Unit,
    onSignInSucceed: () -> Unit,
    viewModel: SignInViewModel = viewModel()
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
        val account by viewModel.account.collectAsState()
        val password by viewModel.password.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        val (ivBack, title, accountLayout, passwordLayout, signInBtn, forgetBtn, nextButton, bottomImage) = createRefs()

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
            text = stringResource(id = R.string.title_sign_in),
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
            },
            onAccountChanged = {
                viewModel.updateAccount(it)
            })

        PasswordInput(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(passwordLayout) {
                    start.linkTo(parent.start)
                    top.linkTo(accountLayout.bottom, 20.dp)
                    end.linkTo(parent.end)
                },
            placeHolder = "请输入密码",
            onPasswordChanged = {
                viewModel.updatePassword(it)
            })

        Text(
            text = "新用户注册",
            fontSize = 14.sp,
            color = colorResource(id = R.color.color_sub_title),
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp, 0.dp)
                .clickable(onClick = onSignUpClicked)
                .constrainAs(signInBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(passwordLayout.bottom, 16.dp)
                })

        Text(
            text = "忘记密码",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp, 0.dp)
                .clickable(onClick = onForgetPasswordClicked)
                .constrainAs(forgetBtn) {
                    top.linkTo(signInBtn.top)
                    end.linkTo(parent.end)
                })

        wideSolidButton(
            text = "登录",
            modifier = Modifier
                .width(220.dp)
                .height(44.dp)
                .constrainAs(nextButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(signInBtn.bottom, 40.dp)
                },
            enabled = account.isNotEmpty() && password.isNotEmpty(),
            onClick = {
                keyboard?.hide()
                viewModel.signIn()
            }
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun DisplayScreenPreview() {
    SignInScreen(
        onBack = { /*TODO*/ },
        onSignUpClicked = { /*TODO*/ },
        onForgetPasswordClicked = { /*TODO*/ },
        onSignInSucceed = { /*TODO*/ })
}