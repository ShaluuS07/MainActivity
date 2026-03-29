package com.example.mainactivity.mvvm.profile.model

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileDismissStore(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private val _dismissedIds = MutableStateFlow(load())
    val dismissedIds: StateFlow<Set<Long>> = _dismissedIds.asStateFlow()

    private fun load(): Set<Long> {
        val set = prefs.getStringSet(KEY_SET, null) ?: return emptySet()
        return set.mapNotNull { it.toLongOrNull() }.toSet()
    }

    fun dismiss(id: Long) {
        val next = _dismissedIds.value + id
        prefs.edit().putStringSet(KEY_SET, next.map { it.toString() }.toSet()).apply()
        _dismissedIds.value = next
    }

    fun clearAll() {
        prefs.edit().remove(KEY_SET).apply()
        _dismissedIds.value = emptySet()
    }


    fun retainOnlyExistingIds(validIds: Set<Long>) {
        val next = _dismissedIds.value.intersect(validIds)
        if (next == _dismissedIds.value) return
        if (next.isEmpty()) {
            prefs.edit().remove(KEY_SET).apply()
        } else {
            prefs.edit().putStringSet(KEY_SET, next.map { it.toString() }.toSet()).apply()
        }
        _dismissedIds.value = next
    }

    companion object {
        private const val PREFS = "matrimony_profile_dismiss"
        private const val KEY_SET = "dismissed_ids"
    }
}
