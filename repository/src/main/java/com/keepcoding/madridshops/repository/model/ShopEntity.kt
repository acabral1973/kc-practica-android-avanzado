package com.keepcoding.madridshops.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ShopEntity(
        val id: Long,
        val databaseId: Long,
        val name: String,
        val description_en: String,
        var gps_lat: String,
        val gps_lon: String,
        val img: String,
        val logo_img: String,
        val opening_hours_en: String,
        val address: String
)