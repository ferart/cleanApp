package com.example.envoy.cleanapp.modelview

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.example.data.cache.BusinessRoomDataStore
import com.example.data.repository.BusinessDataRepository
import com.example.data.repository.LocationDataRepository
import com.example.data.repository.datastores.BusinessDataStore
import com.example.domain.models.Business
import com.example.domain.models.Location
import com.example.domain.repository.BusinessRespository
import com.example.domain.repository.LocationRepository
import com.example.domain.usecases.browsebusiness.SearchBusinessUseCase
import com.example.envoy.cleanapp.model.BusinessViewEntity
import com.example.vds.remote.BusinessGraphQLDataStore


class BusinessViewModel(application: Application) : BaseViewModel(application) {

    var bestBusinessesAround: MutableLiveData<List<BusinessViewEntity>> = MutableLiveData()

    /**
     * Ugh this is ugly, we should use DI
     * Inject all dependencies using Dagger or Koin
     */
    private val cacheDataStore: BusinessDataStore.BusinessCacheDataStore = BusinessRoomDataStore((this.getApplication() as Application).applicationContext)
    private val remoteDataStore: BusinessDataStore.BusinessRemoteDataStore = BusinessGraphQLDataStore()
    private val businessRepository: BusinessRespository = BusinessDataRepository(cacheDataStore, remoteDataStore)
    private val locationRepository: LocationRepository = LocationDataRepository()
    private val searchBusinessUC = SearchBusinessUseCase(businessRepository, locationRepository)

    fun searchBusinessByLocationAndType(location: String?, type: String?) {
        //implement logic to split token, for simplicity, let's use one field containing all the location
        val location = Location(location)
        searchBusinessUC(SearchBusinessUseCase.Params(location, type)) {
            it.either(::handleFailure, ::handleBusinessesFound)
        }
    }

    private fun handleBusinessesFound(businesses: List<Business>) {
        val businessesViewRepresentation = businesses.map {
            BusinessViewEntity(it.name,
                    it.alias,
                    it.rating,
                    it.reviewCount,
                    it.categories?.get(0)?.alias,
                    it.location?.street,
                    it.location?.city,
                    it.location?.state,
                    it.location?.country,
                    it.urlPhoto)
        }
        this.bestBusinessesAround.value = businessesViewRepresentation
    }


}