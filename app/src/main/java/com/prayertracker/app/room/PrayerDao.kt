package com.prayertracker.app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePrayer(
        prayer: PrayerEntity
    )

    @Query(
        "SELECT * FROM prayers ORDER BY date DESC"
    )
    suspend fun getAll(): List<PrayerEntity>

    @Query(
        "SELECT * FROM prayers WHERE date = :date LIMIT 1"
    )
    suspend fun getByDate(
        date: String
    ): PrayerEntity?
}
