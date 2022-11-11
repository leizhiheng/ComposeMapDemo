package com.ubt.composemapdemo.ui.map

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.PointF
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.Entity
import com.ubt.composemapdemo.dp2Px
import com.ubtrobot.mapview.VirtualWall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MapViewModel(app: Application): AndroidViewModel(app) {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val repository = MapViewRepository()

    private var _virtualWalls: MutableLiveData<MutableList<VirtualWall>> = MutableLiveData(mutableListOf())
    var virtualWalls = _virtualWalls

    var tempVirtualWalls = Transformations.switchMap(_virtualWalls) {
        MutableLiveData<MutableList<VirtualWall>>(mutableListOf()).apply {
            this.value!!.addAll(it)
        }
    }

    private var _canvasEnable: MutableLiveData<Boolean> = MutableLiveData(false)
    var canvasEnable = _canvasEnable

    private var _isUpdateDialogOpen: MutableLiveData<Boolean> = MutableLiveData(false)
    var isUpdateDialogOpen = _isUpdateDialogOpen

    fun getWalls() {
        uiScope.launch(Dispatchers.IO) {
            _virtualWalls.value!!.clear()
            _virtualWalls.value!!.addAll(repository.getVirtualWall())
        }
    }

    fun insetWalls(walls: List<VirtualWall>) {
        uiScope.launch(Dispatchers.IO) {
            repository.insertVirtualWall(walls)
        }
    }

    fun deleteWall(wall: VirtualWall) {
        uiScope.launch(Dispatchers.IO) {
            repository.deleteWall(wall)
        }
    }
}

sealed class VirtualWallState {
    object Editing: VirtualWallState()
    object Idle: VirtualWallState()
}