package com.example.data.repository

import com.example.domain.models.Location
import com.example.domain.repository.LocationRepository

class LocationDataRepository: LocationRepository {


    override fun getCurrentLocation(): Location? {
        //"Implement a LocationRemoteDataStore with location capabilities using GPS")
        return Location("Tesla", "Irvine", "California", "US")
    }

    override fun getLocationByLatLon(lat: Float, lon: Float): Location? {
        TODO("Implement geocoding using a LocationRemoteDataStore")
    }
}