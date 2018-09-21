package com.example.data.mappers

import com.example.data.models.BusinessEntity
import com.example.domain.mappers.Mapper
import com.example.domain.models.Business
import com.example.domain.models.Category
import com.example.domain.models.Location

object BusinessRepositoryMapper : Mapper<BusinessEntity,Business>{


    override fun mapToInnerEntity(outerEntity: BusinessEntity): Business {
        return Business(
                outerEntity.name,
                outerEntity.alias,
                outerEntity.rating,
                outerEntity.reviewCount,
                listOf(Category(alias = outerEntity.categoryName)),
                Location(outerEntity.address?.street),
                outerEntity.urlPhoto)
    }

    override fun mapToOuterEntity(innerEntity: Business): BusinessEntity {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}