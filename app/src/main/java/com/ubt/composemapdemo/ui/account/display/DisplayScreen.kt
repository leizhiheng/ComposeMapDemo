package com.ubt.composemapdemo.ui.account.display

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.showToast
import com.ubt.composemapdemo.ui.account.wideSolidButton
import com.ubt.composemapdemo.ui.account.wideStrokeButton

@Composable
fun DisplayScreen(
    onUserAgreementClicked: () -> Unit,
    onOpenPrivacyPolicyClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    viewModel: DisplayViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_device_sample_bg),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Image(
                painter = painterResource(id = R.drawable.ic_device_sample),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 79.dp, 32.dp, 10.dp),
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = stringResource(id = R.string.product_name),
            fontSize = 60.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 1.dp)
                .wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(48.dp))

        val isAgreed = viewModel.isAgreedPolicy.collectAsState()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()
                .padding(20.dp, 0.dp)
        ) {
            Image(
                painter = painterResource(id = if (isAgreed.value) R.drawable.ic_checkbox_sel else R.drawable.ic_checkbox_unsel),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        viewModel.updateAgreementState()
                    }
            )

            Spacer(modifier = Modifier.width(5.dp))

            AnnotationAgreementAndPolicyText(onUserAgreementClicked, onOpenPrivacyPolicyClicked)
        }

        Spacer(modifier = Modifier.height(32.dp))
        wideSolidButton(
            text = "登录",
            modifier = Modifier
                .width(220.dp)
                .height(44.dp),
            onClick = {
                if (isAgreed.value) {
                    onSignInClicked.invoke()
                } else {
                    showToast("请阅读并同意《用户协议》和《隐私政策》")
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        wideStrokeButton(
            text = "新用户注册",
            modifier = Modifier
                .width(220.dp)
                .height(44.dp),
            onClick = {
                if (isAgreed.value) {
                    onSignUpClicked.invoke()
                } else {
                    showToast("请阅读并同意《用户协议》和《隐私政策》")
                }
            }
        )
    }
}

@Composable
fun AnnotationAgreementAndPolicyText(
    onUserAgreementClicked: () -> Unit,
    onOpenPrivacyPolicyClicked: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.color_sub_title),
                fontSize = 14.sp
            )
        ) {
            append("已阅读并同意 ")
        }

        pushStringAnnotation(tag = "agreement", annotation = "")
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("《用户协议》")
        }
        pop()

        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.color_sub_title),
                fontSize = 14.sp
            )
        ) {
            append(" 和 ")
        }

        pushStringAnnotation(tag = "policy", annotation = "")
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("《隐私政策》")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "agreement", start = offset,
                end = offset
            ).firstOrNull()?.let {
                onUserAgreementClicked.invoke()
            }

            annotatedText.getStringAnnotations(
                tag = "policy", start = offset,
                end = offset
            ).firstOrNull()?.let {
                onOpenPrivacyPolicyClicked.invoke()
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun DisplayScreenPreview() {
    DisplayScreen(
        onUserAgreementClicked = { },
        onOpenPrivacyPolicyClicked = { },
        onSignInClicked = { },
        onSignUpClicked = { })
}