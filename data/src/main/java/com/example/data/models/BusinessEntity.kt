package com.example.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "db_business")
class BusinessEntity {

    @PrimaryKey
    var businessID: String = ""

    var name: String? = null

    var alias: String? = null

    var rating: Float? = null

    var reviewCount: Int? = null

    //use as a FK, many to many relationship, break with a third table
    //for this example, let's not use this normalization
    var categoryName: String? = null

    @Embedded
    var address: LocationEntity? = null

    var urlPhoto: String?= null

}