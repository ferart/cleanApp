package com.example.data.repository.datastores

import com.example.data.models.BusinessEntity
import com.example.data.models.LocationEntity
import com.example.domain.exceptions.Failure
import com.example.domain.usecases.Either

class BusinessDataStore<out Type> where Type : Any {

    interface BaseDataStore<Type>{
        suspend fun getBusinessByLocationAndType(address: LocationEntity?, type: String? = null): List<BusinessEntity>
    }

    interface BusinessCacheDataStore : BaseDataStore<List<BusinessEntity>>{
        fun saveBusiness(businessEntity: BusinessEntity)

        fun deleteBusiness(businessEntity: BusinessEntity)

        fun isCacheExpired(): Boolean
    }

    interface BusinessRemoteDataStore: BaseDataStore<List<BusinessEntity>>
}