package com.jmr.poi.data.database

import androidx.room.Dao
import androidx.room.Insert
import com.jmr.poi.domain.model.poi.Poi

@Dao
interface PoiDao {

    @Insert
    fun insert(poi: Poi)

    @Insert
    fun insertAll(vararg poi: Poi)

    @androidx.room.Query("SELECT * FROM pois ORDER BY id DESC")
    fun getAll(): List<Poi>

    @androidx.room.Query("DELETE FROM pois")
    fun nukeTable()
}