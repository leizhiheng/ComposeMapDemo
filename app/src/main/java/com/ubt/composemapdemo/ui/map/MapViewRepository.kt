package com.ubt.composemapdemo.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubt.composemapdemo.App
import com.ubt.composemapdemo.dp2Px
import com.ubt.composemapdemo.ui.map.dao.VirtualWallDao
import com.ubt.composemapdemo.ui.map.dao.VirtualWallDatabase
import com.ubt.composemapdemo.ui.repository.BaseRepository
import com.ubtrobot.mapview.VirtualWall
import kotlinx.coroutines.Dispatchers

class MapViewRepository: BaseRepository() {

    private var virtualWallDb: VirtualWallDatabase = VirtualWallDatabase.getInstance(App.getApp())
    private var virtualWallDao: VirtualWallDao = virtualWallDb.virtualWallDao()

     fun getVirtualWall(): List<VirtualWall> {
    //        return mutableListOf<VirtualWall>().apply {
    //            add(VirtualWall(System.nanoTime(), dp2Px(30f), dp2Px(200f), dp2Px(130f), dp2Px(300f)))
    //            add(VirtualWall(System.nanoTime(), dp2Px(150f), dp2Px(210f), dp2Px(220f), dp2Px(310f)))
    //            add(VirtualWall(System.nanoTime(), dp2Px(120f), dp2Px(350f), dp2Px(220f), dp2Px(460f)))
    //        }
         return virtualWallDao.getAll()
    }

    fun insertVirtualWall(walls: List<VirtualWall>) {
        virtualWallDao.insertAll(walls)
    }

    fun deleteWall(wall: VirtualWall) {
        virtualWallDao.delete(wall)
    }
}