package com.example.mainactivity.mvvm.profile

import com.example.mainactivity.mvvm.profile.model.ProfileDao
import com.example.mainactivity.mvvm.profile.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

/** Profile detail screen: single profile stream only. */
class ProfileDetailsRepository(private val dao: ProfileDao) {

    fun observeProfile(id: Long): Flow<ProfileEntity?> = dao.observeById(id)
}
