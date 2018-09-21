package com.example.data.mappers

import com.example.data.models.LocationEntity
import com.example.domain.mappers.Mapper
import com.example.domain.models.Location

object LocationRepositoryMapper : Mapper<LocationEntity, Location> {


    override fun mapToInnerEntity(outerEntity: LocationEntity): Location {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mapToOuterEntity(innerEntity: Location): LocationEntity {
        val locationEntity = LocationEntity()
        locationEntity.street = innerEntity.street ?: ""
        locationEntity.city = innerEntity.city ?: ""
        locationEntity.state = innerEntity.state ?: ""
        locationEntity.country = innerEntity.country ?: ""
        return locationEntity
    }
}