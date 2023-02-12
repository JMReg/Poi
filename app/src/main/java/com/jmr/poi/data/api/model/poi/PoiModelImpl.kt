package com.jmr.poi.data.api.model.poi

import android.app.Application
import com.jmr.poi.PoiApp
import com.jmr.poi.data.database.AppDatabase
import com.jmr.poi.data.repository.poi.PoiRepository
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class PoiModelImpl @Inject constructor(
    private val repositoryImpl: PoiRepository
) : PoiModel {

    override fun get(): Flow<AppResult<List<Poi>>> {
        return repositoryImpl.requestPoiList().transform { result ->
            emit(result)
        }
    }

    override fun get(id: String): Flow<AppResult<Poi>> {
        val currentList = repositoryImpl.getCurrentList()
        return flowOf(
            if (currentList.isNotEmpty()) {
                AppResult.success(currentList.last {
                    it.idPoi == id
                })
            } else {
                AppResult.exception(Throwable())
            }
        )
    }

}