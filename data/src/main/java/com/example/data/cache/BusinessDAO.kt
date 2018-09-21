package com.example.data.cache

import android.arch.persistence.room.*
import com.example.data.models.BusinessEntity

@Dao
interface BusinessDAO {

    @Query("SELECT * FROM db_business WHERE street LIKE :street AND city LIKE :city AND state LIKE :state AND categoryName LIKE :type") //just for the example sake, simplify address
    fun getBusinessesByLocationAndCategory(street: String?, city: String?, state: String?, type: String?): List<BusinessEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(businessEntity: BusinessEntity)

    @Delete
    fun delete(businessEntity: BusinessEntity)

}