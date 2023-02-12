package com.jmr.poi.ui.poi.list


import android.util.Log
import androidx.lifecycle.*
import com.jmr.poi.domain.base.AppResult
import com.jmr.poi.domain.model.poi.Poi
import com.jmr.poi.domain.usecase.poi.RequestPoiListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoiListViewModel @Inject constructor(
    private val requestPoiListUseCase: RequestPoiListUseCase
) : ViewModel() {

    private val poiList = MutableLiveData<AppResult<List<Poi>>>()

    fun requestPoiList() {
        viewModelScope.launch {
            requestPoiListUseCase.execute()
                .catch {
                    val exception = it
                    Log.v("Exception", "Exception", exception)
                }.collect {
                    poiList.postValue(it)
                }
        }
    }

    fun getPoiList(): MutableLiveData<AppResult<List<Poi>>> {
        return poiList
    }
}
