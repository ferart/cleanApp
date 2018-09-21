package com.example.domain.mappers

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 */
interface Mapper<Outer, Inner> {

    fun mapToInnerEntity(outerEntity: Outer): Inner

    fun mapToOuterEntity(innerEntity: Inner): Outer
}