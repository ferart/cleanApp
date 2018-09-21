package com.example.data.repository

import com.example.data.failures.DataFailures
import com.example.data.mappers.BusinessRepositoryMapper
import com.example.data.mappers.LocationRepositoryMapper
import com.example.data.models.BusinessEntity
import com.example.data.repository.datastores.BusinessDataStore
import com.example.domain.exceptions.Failure
import com.example.domain.models.Business
import com.example.domain.models.Location
import com.example.domain.repository.BusinessRespository
import com.example.domain.usecases.Either

class BusinessDataRepository(private val businessCacheDataStore: BusinessDataStore.BusinessCacheDataStore,
                             private val businessRemoteDataStore: BusinessDataStore.BusinessRemoteDataStore) : BusinessRespository {

    override suspend fun getBusinessByLocationAndType(location: Location?, type: String?): Either<Failure, List<Business>> {
        val businessFound: List<BusinessEntity>?
        val businesses = mutableListOf<Business>()
        val locationEntity = location?.let { LocationRepositoryMapper.mapToOuterEntity(it) }
        businessFound = if (businessCacheDataStore.isCacheExpired()) {
            //fetch remote data
            businessRemoteDataStore.getBusinessByLocationAndType(locationEntity, type)
        } else {
            //return cache data
            businessCacheDataStore.getBusinessByLocationAndType(locationEntity, type)
        }

        return if (businessFound.isNotEmpty()) {
            businessFound.forEach { businesses.add(BusinessRepositoryMapper.mapToInnerEntity(it)) }
            Either.Right(businesses)
        } else {
            Either.Left(DataFailures.ListNotAvailable())
        }
    }

    override suspend fun saveBusiness(business: Business) {
        val businessEntity = BusinessRepositoryMapper.mapToOuterEntity(business)
        businessCacheDataStore.saveBusiness(businessEntity)
    }

    override suspend fun deleteBusiness(business: Business) {
        val businessEntity = BusinessRepositoryMapper.mapToOuterEntity(business)
        businessCacheDataStore.deleteBusiness(businessEntity)
    }
}