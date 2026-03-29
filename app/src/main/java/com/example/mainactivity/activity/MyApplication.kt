package com.example.mainactivity.activity

import android.app.Application
import android.content.Context
import com.example.mainactivity.mvvm.profile.model.ProfileDatabase
import com.example.mainactivity.mvvm.home.HomeRepository
import com.example.mainactivity.mvvm.profile.ProfileDetailsRepository
import com.example.mainactivity.mvvm.recommendations.RecommendationsRepository
import com.example.mainactivity.mvvm.profile.model.ProfileDao
import com.example.mainactivity.mvvm.profile.model.ProfileDismissStore
import com.example.mainactivity.mvvm.profile.model.ProfileSeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MyApplication : Application() {

    lateinit var homeRepository: HomeRepository
        private set
    lateinit var recommendationsRepository: RecommendationsRepository
        private set
    lateinit var profileDetailsRepository: ProfileDetailsRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val dao = ProfileDatabase.getInstance(this).profileDao()
        val dismissStore = ProfileDismissStore(this)
        dismissStore.clearAll()
        runBlocking(Dispatchers.IO) {
            seedIfNeeded(dao, dismissStore)
        }
        homeRepository = HomeRepository(dao, dismissStore)
        recommendationsRepository = RecommendationsRepository(dao, dismissStore)
        profileDetailsRepository = ProfileDetailsRepository(dao)
    }

    private suspend fun seedIfNeeded(dao: ProfileDao, dismissStore: ProfileDismissStore) {
        val prefs = getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE)
        val seedVersion = prefs.getInt(KEY_SEED_VERSION, 0)
        if (seedVersion < CURRENT_SEED_VERSION) {
            dismissStore.clearAll()
            dao.deleteAll()
            dao.insertAll(ProfileSeed.data)
            prefs.edit().putInt(KEY_SEED_VERSION, CURRENT_SEED_VERSION).apply()
        } else if (dao.count() == 0) {
            dismissStore.clearAll()
            dao.insertAll(ProfileSeed.data)
        }
        dismissStore.retainOnlyExistingIds(dao.getAllIds().toSet())
    }

    companion object {
        private const val PREFS_APP = "matrimony_app"
        private const val KEY_SEED_VERSION = "profile_seed_version"
        private const val CURRENT_SEED_VERSION = 5
    }
}
