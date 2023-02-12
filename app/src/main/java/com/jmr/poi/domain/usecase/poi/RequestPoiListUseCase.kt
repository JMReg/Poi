package com.jmr.poi.domain.usecase.poi

import com.jmr.poi.data.api.model.poi.PoiModelImpl
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.base.FlowUseCase
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestPoiListUseCase @Inject constructor(
    private val poiModelImpl: PoiModelImpl
) : FlowUseCase<AppResult<List<Poi>>>() {

    public override fun execute(): Flow<AppResult<List<Poi>>> {
        return poiModelImpl.get()
    }
}