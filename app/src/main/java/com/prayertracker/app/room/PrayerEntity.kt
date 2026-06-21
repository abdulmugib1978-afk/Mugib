package com.prayertracker.app.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayers")
data class PrayerEntity(

    @PrimaryKey
    val date: String,

    val fajr: Boolean,
    val dhuhr: Boolean,
    val asr: Boolean,
    val maghrib: Boolean,
    val isha: Boolean
)
