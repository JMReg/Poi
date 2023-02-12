package com.jmr.poi.data.repository.poi

import android.app.Application
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.data.api.ApiService
import com.jmr.poi.data.api.model.poi.response.inner.toPoi
import com.jmr.poi.data.database.AppDatabase
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val application: Application,
    private val apiService: ApiService
) : PoiRepository {

    lateinit var list: List<Poi>

    override fun requestPoiList() = flow {
        emit(AppResult.loading())

        val db = AppDatabase.getInstance(application)
        list = db.poiDao().getAll().ifEmpty {
            val response = apiService.requestPois()
            val responseList = response.list!!

            responseList.map {
                db.poiDao().insertAll(it.toPoi())
                it.toPoi()
            }
        }

        emit(
            AppResult.success(list)
        )

    }.catch {
        emit(
            AppResult.exception(it)
        )
    }.flowOn(Dispatchers.IO)

    override fun getCurrentList(): List<Poi> {
        return list
    }
}