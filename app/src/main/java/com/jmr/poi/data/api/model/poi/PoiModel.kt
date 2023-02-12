package com.jmr.poi.data.api.model.poi

import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.flow.Flow

interface PoiModel {

    fun get(): Flow<AppResult<List<Poi>>>

    fun get(id: String): Flow<AppResult<Poi>>

}
