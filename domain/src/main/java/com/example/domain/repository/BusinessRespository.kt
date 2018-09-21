package com.example.domain.repository


import com.example.domain.exceptions.Failure
import com.example.domain.models.Business
import com.example.domain.models.Location
import com.example.domain.usecases.Either

interface BusinessRespository {

    suspend fun getBusinessByLocationAndType(location: Location?, type: String? = null): Either<Failure, List<Business>>

    suspend fun saveBusiness(business: Business)

    suspend fun deleteBusiness(business: Business)
}