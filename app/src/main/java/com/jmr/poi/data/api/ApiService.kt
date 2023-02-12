package com.jmr.poi.data.api

import com.jmr.poi.data.api.model.poi.response.ResponsePoiList
import retrofit2.http.GET

interface ApiService {

    @GET("pois.json")
    suspend fun requestPois(): ResponsePoiList

}
