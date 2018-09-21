package com.example.envoy.cleanapp.model

data class BusinessViewEntity(
    var name: String? = null,
    var alias: String? = null,
    var rating: Float? = null,
    var reviewCount: Int? = null,
    var businessType: String? = null,
    var street: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var urlPhoto: String? = null
)