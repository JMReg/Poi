package com.jmr.poi.data.api.model.poi.response

import com.jmr.poi.data.api.model.poi.response.inner.ResponsePoiData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.jmr.poi.domain.model.poi.Poi


@JsonClass(generateAdapter = true)
data class ResponsePoiList(
    @Json(name = "list")
    var list: List<ResponsePoiData>?
)