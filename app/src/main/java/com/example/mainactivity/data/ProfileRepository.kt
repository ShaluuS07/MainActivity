package com.example.mainactivity.data

import com.example.mainactivity.data.local.ProfileDao
import com.example.mainactivity.data.local.ProfileEntity
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val dao: ProfileDao) {

    fun observeProfiles(): Flow<List<ProfileEntity>> = dao.observeAll()

    fun observeProfile(id: Long): Flow<ProfileEntity?> = dao.observeById(id)

    suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    suspend fun delete(profile: ProfileEntity) {
        dao.delete(profile)
    }
}
