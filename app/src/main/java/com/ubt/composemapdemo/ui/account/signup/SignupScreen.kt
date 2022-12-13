@file:OptIn(ExperimentalMaterial3Api::class)

package com.ubt.composemapdemo.ui.account.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubt.composemapdemo.R

@Composable
fun SignupScreen(
    onBack: () -> Unit,
    onAccountChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onNextStep: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    }

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (ivBack, title, accountLayout, passwordLayout, nextButton, bottomImage) = createRefs()
        Button(
            onClick = onBack,
            modifier = Modifier
                .constrainAs(ivBack) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, 66.dp)
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arr), contentDescription = ""
            )
        }

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
            }, onAccountChanged = {})
    }
}

@Composable
fun AccountInput(modifier: Modifier, onAccountChanged: (String) -> Unit) {
    var account by remember { mutableStateOf("") }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentColor = Color.White
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
                contentDescription = ""
            )
            OutlinedTextField(
                value = "",
                onValueChange = onAccountChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent),
                placeholder = @Composable { Text(text = "请输入手机号码/邮箱") },
                trailingIcon = @Composable {
                    Image(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear icon",
                        modifier = Modifier.clickable { account = "" }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onBack = {},
        onAccountChanged = { s -> {} },
        onPasswordChanged = { s -> },
        onNextStep = {})
//    AccountInput() {
//
//    }
}