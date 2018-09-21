package com.example.domain.models

data class Business(
        val name: String? = null,
        val alias: String? = null,
        val rating: Float? = null,
        val reviewCount: Int? = null,
        val categories: List<Category>? = null,
        val location: Location? = null,
        val urlPhoto: String?= null
)