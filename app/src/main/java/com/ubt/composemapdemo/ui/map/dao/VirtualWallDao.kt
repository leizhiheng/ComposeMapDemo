package com.ubt.composemapdemo.ui.map.dao

import androidx.room.*
import com.ubtrobot.mapview.VirtualWall

@Dao
interface VirtualWallDao {
    @Query("SELECT * FROM virtual_wall")
    fun getAll(): List<VirtualWall>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(walls: List<VirtualWall>)

    @Delete
    fun delete(user: VirtualWall)
}