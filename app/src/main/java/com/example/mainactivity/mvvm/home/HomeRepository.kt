package com.example.mainactivity.mvvm.home

import com.example.mainactivity.mvvm.profile.model.ProfileDao
import com.example.mainactivity.mvvm.profile.model.ProfileDismissStore
import com.example.mainactivity.mvvm.profile.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class HomeRepository(
    private val dao: ProfileDao,
    private val dismissStore: ProfileDismissStore,
) {

    fun observeProfiles(): Flow<List<ProfileEntity>> = combine(
        dao.observeAll(),
        dismissStore.dismissedIds,
    ) { list, dismissed ->
        list.filter { it.id !in dismissed }
    }

    fun dismiss(id: Long) {
        dismissStore.dismiss(id)
    }
}
