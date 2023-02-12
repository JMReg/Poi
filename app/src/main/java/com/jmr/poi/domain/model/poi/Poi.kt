package com.jmr.poi.domain.model.poi

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel

@Entity(tableName = "pois")
data class Poi(
    val idPoi: String,
    val title: String? = "",
    val geolocation: String? = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val image: String? = null
) {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}
