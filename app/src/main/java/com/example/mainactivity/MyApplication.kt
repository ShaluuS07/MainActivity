package com.example.mainactivity

import android.app.Application
import android.content.Context
import com.example.mainactivity.data.ProfileRepository
import com.example.mainactivity.data.local.ProfileDatabase
import com.example.mainactivity.data.local.ProfileSeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyApplication : Application() {

    lateinit var repository: ProfileRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = ProfileRepository(ProfileDatabase.getInstance(this).profileDao())
        CoroutineScope(Dispatchers.IO).launch {
            val dao = ProfileDatabase.getInstance(applicationContext).profileDao()
            val prefs = getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE)
            val expectedCount = ProfileSeed.data.size
            val seedVersion = prefs.getInt(KEY_SEED_VERSION, 0)
            // One-time resync so older DBs (e.g. 5 or 15 rows) match current seed size; does not run again after bump.
            if (seedVersion < CURRENT_SEED_VERSION) {
                dao.deleteAll()
                dao.insertAll(ProfileSeed.data)
                prefs.edit().putInt(KEY_SEED_VERSION, CURRENT_SEED_VERSION).apply()
            } else if (dao.count() == 0) {
                dao.insertAll(ProfileSeed.data)
            }
        }
    }

    companion object {
        private const val PREFS_APP = "matrimony_app"
        private const val KEY_SEED_VERSION = "profile_seed_version"
        /** Bump when [ProfileSeed] row count or IDs change so installs upgrade data once. */
        private const val CURRENT_SEED_VERSION = 3
    }
}
