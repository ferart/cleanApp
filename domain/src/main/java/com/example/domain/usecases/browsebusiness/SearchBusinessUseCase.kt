package com.example.domain.usecases.browsebusiness

import com.example.domain.exceptions.Failure
import com.example.domain.models.Business
import com.example.domain.models.Location
import com.example.domain.repository.BusinessRespository
import com.example.domain.repository.LocationRepository
import com.example.domain.usecases.BaseUseCase
import com.example.domain.usecases.Either

class SearchBusinessUseCase(private val businessRepository: BusinessRespository,
                            private val locationRepository: LocationRepository) : BaseUseCase<SearchBusinessUseCase.Params, List<Business>>() {


    override suspend fun run(params: Params?): Either<Failure, List<Business>> {
        var parameters = params
        if (parameters == null) {
            parameters = Params(locationRepository.getCurrentLocation())
        }
        val result = businessRepository.getBusinessByLocationAndType(parameters.location, parameters.type)
        return result
    }

    data class Params(val location: Location?, val type: String? = null)
}