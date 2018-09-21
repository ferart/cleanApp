package com.example.vds.mappers

import com.example.data.models.BusinessEntity
import com.example.data.models.LocationEntity
import com.example.domain.mappers.Mapper

object SearchBusinessMapper : Mapper<SearchBusinessQuery.Business,BusinessEntity>{


    override fun mapToInnerEntity(outerEntity: SearchBusinessQuery.Business): BusinessEntity {
        val businessEntity = BusinessEntity()
        businessEntity.name = outerEntity.name()
        businessEntity.alias = outerEntity.alias()
        businessEntity.rating = outerEntity.rating()?.toFloat()
        businessEntity.reviewCount = outerEntity.review_count()
        businessEntity.categoryName = outerEntity.categories()?.get(0)?.alias()
        val locationEntity = LocationEntity()
        locationEntity.street = outerEntity.location()?.formatted_address() //be smarter about this
        businessEntity.address = locationEntity
        businessEntity.urlPhoto = outerEntity.photos()?.get(0)

        return businessEntity
    }

    override fun mapToOuterEntity(innerEntity: BusinessEntity): SearchBusinessQuery.Business {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}