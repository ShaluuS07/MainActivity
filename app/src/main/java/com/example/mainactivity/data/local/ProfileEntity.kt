package com.example.mainactivity.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val age: Int,
    val height: String,
    val language: String,
    val religionCommunity: String,
    val education: String,
    val profession: String,
    val city: String,
    val stateCountry: String,
    val profileIdCode: String,
    val isVerified: Boolean,
    val isPremiumNri: Boolean,
    val photoCount: Int,
)
