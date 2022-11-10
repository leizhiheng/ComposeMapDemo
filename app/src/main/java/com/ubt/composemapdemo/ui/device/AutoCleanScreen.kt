package com.ubt.composemapdemo.ui.device

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ubt.composemapdemo.R
import com.ubt.composemapdemo.showToast
import com.ubt.composemapdemo.ui.commomcomposable.widget.ThreeOperationChoice
import com.ubt.composemapdemo.ui.navigation.Router
import com.ubt.composemapdemo.ui.theme.ComposeMapDemoTheme

@Composable
fun AutoCleanScreen() {
    ComposeMapDemoTheme {
        Surface(modifier = Modifier.background(Color.DarkGray)) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFEFEFF0))
                    .fillMaxSize()
            ) {
                //title bar
                val onBack: () -> Unit = {
                    Router.popBack()
                }
                EasyBar(onBack, "自动清洁") {
                    Router.toMapDisplayScreen()
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    DeviceState(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(), resId = R.drawable.ic_sweeper
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val width = size.width
                            drawCircle(Color.Green, width / 2f)
                        }
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "待机状态", fontSize = 10.sp)
                        Spacer(Modifier.weight(1f))

                        Text(text = "100%", fontSize = 10.sp)
                        Image(
                            painter = painterResource(id = R.drawable.ic_battery),
                            contentDescription = "",
                            Modifier
                                .size(25.dp)
                                .clip(
                                    CircleShape
                                )
                        )
                    }

                    //divider
                    Divider(
                        //颜色
                        color = Color.Black,
                        //线的高度
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        //水箱状态
                        DoubleIconCard(
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp),
                            iconIdLeft = R.drawable.ic_clean_tank_normal,
                            iconIdRight = R.drawable.ic_dirty_tank_normal
                        )

                        //抹布已清洁
                        SingleIconCard(
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp), iconId = R.drawable.ic_mop_cleaned, str = "抹布已清洁"
                        )

                        //尘盒正常
                        SingleIconCard(
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp), iconId = R.drawable.ic_dust_box_normal, str = "尘盒正常"
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    //操作按钮
                    ThreeOperationChoice(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        iconIdLeft = R.drawable.ic_mode_switch,
                        iconIdMid = R.drawable.ic_start,
                        iconIdRight = R.drawable.ic_mode_switch,
                        strLeft = "模式切换",
                        strRight = "返回基站",
                        onLeftClick = { showToast("模式切换") },
                        onRightClick = { showToast("返回基站") }
                    )
                }
            }

        }
    }
}

@Composable
fun DoubleIconCard(modifier: Modifier, iconIdLeft: Int, iconIdRight: Int) {
    ConstraintLayout(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
            )
        }
        val (ivClean, ivDirty, text) = createRefs()
        Image(
            painter = painterResource(id = iconIdLeft),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .constrainAs(ivClean) {
                    start.linkTo(parent.start, 3.dp)
                    end.linkTo(ivDirty.start)
                    top.linkTo(parent.top, 5.dp)
                }
        )
        Image(
            painter = painterResource(id = iconIdRight),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .constrainAs(ivDirty) {
                    start.linkTo(ivClean.end, 3.dp)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, 5.dp)
                }
        )
        Text(
            text = "水箱正常",
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(text) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(parent.bottom, 10.dp)
            })
    }
}

@Composable
fun SingleIconCard(modifier: Modifier, iconId: Int, str: String) {
    ConstraintLayout(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
            )
        }
        val (icon, text) = createRefs()
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "",
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .constrainAs(icon) {
                    start.linkTo(parent.start, 3.dp)
                    end.linkTo(parent.end, 3.dp)
                    top.linkTo(parent.top, 5.dp)
                }
        )

        Text(
            text = str,
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(text) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(parent.bottom, 10.dp)
            })
    }
}

@Preview
@Composable
fun PreviewAutoClean() {
    AutoCleanScreen()
}

@Composable
fun DeviceState(modifier: Modifier, resId: Int) {
    ConstraintLayout(modifier = modifier) {
        val (deviceImage, ivPref, tvPref, ivCleanMop, tvCleanMop) = createRefs()
        Image(
            painter = painterResource(id = resId),
            contentDescription = "",
            modifier = modifier
                .size(200.dp, 200.dp)
                .constrainAs(deviceImage) {
                    top.linkTo(parent.top, 30.dp)
                    linkTo(start = parent.start, end = parent.end)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_preference_setting),
            contentDescription = "", modifier = Modifier
                .size(40.dp)
                .clickable { showToast("偏好设置") }
                .constrainAs(ivPref) {
                    top.linkTo(deviceImage.bottom)
                    end.linkTo(parent.end, 20.dp)
                }
        )

        Text(text = "偏好设置", fontSize = 12.sp, modifier = Modifier.constrainAs(tvPref) {
            top.linkTo(ivPref.bottom)
            linkTo(start = ivPref.start, end = ivPref.end)
        })

        Image(
            painter = painterResource(id = R.drawable.ic_wash_mop),
            contentDescription = "", modifier = Modifier
                .size(40.dp)
                .clickable { showToast("清洗拖布") }
                .constrainAs(ivCleanMop) {
                    top.linkTo(tvPref.bottom, 20.dp)
                    linkTo(start = ivPref.start, end = ivPref.end)
                }
        )

        Text(text = "清洗拖布", fontSize = 12.sp, modifier = Modifier.constrainAs(tvCleanMop) {
            top.linkTo(ivCleanMop.bottom)
            linkTo(start = ivPref.start, end = ivPref.end)
        })
    }

}


@Composable
fun EasyBar(backOnClick: () -> Unit, titleString: String, moreOnClick: () -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {

        val (ivBack, title, ivMenu) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_back_left),
            contentDescription = "icon back",
            modifier = Modifier
                .size(40.dp, 40.dp)
                .clickable {
                    backOnClick.invoke()
                }
                .constrainAs(ivBack) {
                    start.linkTo(parent.start, margin = 10.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

        Text(
            text = titleString, fontSize = 18.sp,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 50.dp)
                end.linkTo(parent.end, 50.dp)
            }
        )

        Image(painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "icon more",
            modifier = Modifier
                .size(40.dp, 40.dp)
                .padding(6.dp)
                .clickable {
                    moreOnClick.invoke()
                }
                .constrainAs(ivMenu) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewEasyBar() {
    val onBack: () -> Unit = {
        Router.popBack()
    }
    EasyBar(onBack, "自动清洁") {
        showToast(R.string.wait_dep)
    }
}