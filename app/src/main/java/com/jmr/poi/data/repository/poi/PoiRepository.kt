package com.jmr.poi.data.repository.poi

import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.flow.Flow

interface PoiRepository {

    fun requestPoiList(): Flow<AppResult<List<Poi>>>

    fun getCurrentList(): List<Poi>
}
