package com.ubt.composemapdemo.ui.map

import com.ubt.composemapdemo.dp2Px

class MapViewRepository {
    fun getVirtualWall(): List<VirtualWall> {
        return mutableListOf<VirtualWall>().apply {
            add(VirtualWall(dp2Px(30f), dp2Px(200f), dp2Px(130f), dp2Px(300f)))
            add(VirtualWall(dp2Px(150f), dp2Px(210f), dp2Px(220f), dp2Px(310f)))
            add(VirtualWall(dp2Px(120f), dp2Px(350f), dp2Px(220f), dp2Px(460f)))
        }
    }
}