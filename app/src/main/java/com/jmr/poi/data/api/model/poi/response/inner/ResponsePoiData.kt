package com.jmr.poi.data.api.model.poi.response.inner

import com.jmr.poi.domain.model.poi.Poi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponsePoiData(
    @Json(name = "id")
    var id: String,

    @Json(name = "title")
    var title: String,

    @Json(name = "geocoordinates")
    var geocoordinates: String,

    @Json(name = "image")
    var image: String
)

fun ResponsePoiData.toPoi() = Poi(
    idPoi = id,
    title = title,
    geolocation = geocoordinates,
    longitude = getLongitude(geocoordinates),
    latitude = getLatitude(geocoordinates),
    image = image
)

fun getLongitude(geolocation: String): Double? {
    return if (geolocation.isNotEmpty()) {
        val splits = geolocation.split(",")
        if (splits.isNotEmpty() && splits.size > 1) {
            splits[0].toDouble()
        } else {
            null
        }
    } else {
        null
    }
}

fun getLatitude(geolocation: String): Double? {
    return if (geolocation.isNotEmpty()) {
        val splits = geolocation.split(",")
        if (splits.isNotEmpty() && splits.size > 1) {
            splits[1].toDouble()
        } else {
            null
        }
    } else {
        null
    }
}
