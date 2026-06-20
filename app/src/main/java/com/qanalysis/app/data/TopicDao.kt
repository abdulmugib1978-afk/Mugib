package com.qanalysis.app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics ORDER BY id DESC")
    fun getAll(): LiveData<List<TopicEntity>>

    @Insert
    suspend fun insert(topic: TopicEntity)

    @Update
    suspend fun update(topic: TopicEntity)

    @Delete
    suspend fun delete(topic: TopicEntity)
}
