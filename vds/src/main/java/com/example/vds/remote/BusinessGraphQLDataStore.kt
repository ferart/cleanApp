package com.example.vds.remote

import SearchBusinessQuery
import com.example.data.models.BusinessEntity
import com.example.data.models.LocationEntity
import com.example.data.repository.datastores.BusinessDataStore
import com.example.vds.mappers.SearchBusinessMapper

class BusinessGraphQLDataStore : BusinessDataStore.BusinessRemoteDataStore {
    private val apolloClient = GraphQLClient.getApolloClient()

    override suspend fun getBusinessByLocationAndType(address: LocationEntity?, type: String?): List<BusinessEntity> {
        val businesses = mutableListOf<BusinessEntity>()
        val searchBusiness = SearchBusinessQuery.builder()
                .limit(10)
                .location("${address?.street}")
                .term(type!!)
                .build()
        val query = apolloClient.query(searchBusiness)
        val response = query.execute()
        val foundBusiness = response.data()?.search()?.business()
        foundBusiness?.forEach { business: SearchBusinessQuery.Business? ->
            business?.let { businesses.add(SearchBusinessMapper.mapToInnerEntity(it)) }
        }
        return businesses
    }
}