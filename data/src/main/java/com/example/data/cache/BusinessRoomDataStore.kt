package com.example.data.cache

import android.arch.persistence.room.Room
import android.content.Context
import com.example.data.models.BusinessEntity
import com.example.data.models.LocationEntity
import com.example.data.repository.datastores.BusinessDataStore

class BusinessRoomDataStore(val context: Context) : BusinessDataStore.BusinessCacheDataStore {
    private var appDatabase: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "businessFinder")
            .fallbackToDestructiveMigration()
            .build()


    override suspend fun getBusinessByLocationAndType(address: LocationEntity?, type: String?): List<BusinessEntity> {
        return appDatabase.businessDAO().getBusinessesByLocationAndCategory(address?.street, address?.city, address?.state, type)
    }

    override fun saveBusiness(businessEntity: BusinessEntity) {
        appDatabase.businessDAO().insert(businessEntity)
    }

    override fun deleteBusiness(businessEntity: BusinessEntity) {
        appDatabase.businessDAO().delete(businessEntity)
    }

    override fun isCacheExpired(): Boolean {
        //TODO: Implement caching strategy and logic to know expiration Time
        return true
    }
}