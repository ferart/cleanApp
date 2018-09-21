package com.example.domain.repository

import com.example.domain.models.Location


interface LocationRepository {

    fun getCurrentLocation(): Location?

    fun getLocationByLatLon(lat: Float, lon: Float): Location?
}