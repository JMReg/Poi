package com.jmr.poi.ui.poi.detail


import android.util.Log
import androidx.lifecycle.*
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.model.poi.Poi
import com.jmr.poi.domain.usecase.poi.GetPoiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoiDetailViewModel @Inject constructor(
    private val getPoiUseCase: GetPoiUseCase
) : ViewModel() {

    private val poi = MutableLiveData<AppResult<Poi>>()

    fun requestPoi(id: String) {
        viewModelScope.launch {
            getPoiUseCase.execute(GetPoiUseCase.Parameters(id))
                .catch {
                    val exception = it
                    Log.v("Exception", "Exception", exception)
                }.collect {
                    poi.postValue(it)
                }
        }
    }

    fun getPoi(): MutableLiveData<AppResult<Poi>> {
        return poi
    }
}
