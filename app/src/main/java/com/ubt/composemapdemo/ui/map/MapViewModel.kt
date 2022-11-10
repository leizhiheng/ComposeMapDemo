package com.ubt.composemapdemo.ui.map

import android.app.Application
import android.graphics.PointF
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import com.ubt.composemapdemo.dp2Px

class MapViewModel(app: Application): AndroidViewModel(app) {
    private val repository = MapViewRepository()

    private var _virtualWalls = repository.getVirtualWall().toMutableStateList()
    var virtualWalls = _virtualWalls

    private var _tempVirtualWalls = mutableListOf<VirtualWall>().toMutableStateList()
    var tempVirtualWalls = _tempVirtualWalls

    private var _canvasEnable: MutableState<Boolean> = mutableStateOf(false)
    var canvasEnable = _canvasEnable

    private var _isUpdateDialogOpen: MutableState<Boolean> = mutableStateOf(false)
    var isUpdateDialogOpen = _isUpdateDialogOpen

    fun updateWalls() {

    }
}

data class VirtualWall(var left: Float, var top: Float, var right: Float, var bottom: Float, var state: VirtualWallState = VirtualWallState.Idle) {
    companion object {
        var MIN_WIDTH = dp2Px(50)

        fun getOriginalVirtualWall(): VirtualWall {
            return VirtualWall(dp2Px(130f), dp2Px(300f), dp2Px(230f), dp2Px(400f), VirtualWallState.Editing)
        }
    }

    fun getWidth(): Float {
        return right - left
    }

    fun getHeight(): Float {
        return bottom - top;
    }

    fun updateDraggingRect(disX: Float, disY: Float) {
        when (draggingPointIndex) {
            0 -> {
                left += disX
                top += disY

                if (!checkMinWidth()) {
                    left = right - MIN_WIDTH
                }

                if (!checkMinHeight()) {
                    top = bottom - MIN_WIDTH
                }
            }
            1 -> {
                right += disX
                top += disY

                if (!checkMinWidth()) {
                    right = left + MIN_WIDTH
                }

                if (!checkMinHeight()) {
                    top = bottom - MIN_WIDTH
                }
            }
            2 -> {
                left += disX
                bottom += disY

                if (!checkMinWidth()) {
                    left = right - MIN_WIDTH
                }

                if (!checkMinHeight()) {
                    bottom = top + MIN_WIDTH
                }
            }
            3 -> {
                right += disX
                bottom += disY

                if (!checkMinWidth()) {
                    right = left + MIN_WIDTH
                }

                if (!checkMinHeight()) {
                    bottom = top + MIN_WIDTH
                }
            }
        }
    }

    fun checkMinWidth(): Boolean {
        return right - left > MIN_WIDTH
    }

    fun checkMinHeight(): Boolean {
        return bottom - top > MIN_WIDTH
    }

    fun reset() {
        draggingPoint = null
        draggingPointIndex = -1
    }

    var draggingPoint: PointF? = null
    var draggingPointIndex = -1

    var points: MutableList<PointF> = mutableListOf()
        get() {
            field.clear()
            field.add(leftTop)
            field.add(rightTop)
            field.add(leftBottom)
            field.add(rightBottom)
            return field
        }

    var leftTop: PointF = PointF(0f, 0f)
        get() {
            return PointF(left, top)
        }
        set(p) {
            left = p.x
            top = p.y
            field = p
        }

    var rightTop: PointF = PointF(0f, 0f)
        get() {
            return PointF(right, top)
        }
        set(p) {
            right = p.x
            top = p.y
            field = p
        }

    var leftBottom: PointF = PointF(0f, 0f)
        get() {
            return PointF(left, bottom)
        }
        set(p) {
            left = p.x
            bottom = p.y
            field = p
        }

    var rightBottom: PointF = PointF(0f, 0f)
        get() {
            return PointF(right, bottom)
        }
        set(p) {
            right = p.x
            bottom = p.y
            field = p
        }
}

sealed class VirtualWallState {
    object Editing: VirtualWallState()
    object Idle: VirtualWallState()
}