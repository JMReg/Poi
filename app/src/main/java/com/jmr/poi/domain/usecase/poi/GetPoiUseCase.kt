package com.jmr.poi.domain.usecase.poi

import com.jmr.poi.data.api.model.poi.PoiModel
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.base.FlowUseCaseWithParams
import com.jmr.poi.domain.model.poi.Poi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoiUseCase @Inject constructor(
    private val poiModelImpl: PoiModel
) : FlowUseCaseWithParams<GetPoiUseCase.Parameters, AppResult<Poi>>() {

    public override fun execute(parameters: Parameters): Flow<AppResult<Poi>> {
        return poiModelImpl.get(parameters.id)
    }
    class Parameters(
        val id: String
    )
}