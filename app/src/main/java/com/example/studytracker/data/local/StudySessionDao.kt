package com.example.studytracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.studytracker.data.model.StudySession
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {
    @Query("SELECT * FROM study_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<StudySession>>

    @Insert
    suspend fun insert(session: StudySession): Long

    @Delete
    suspend fun delete(session: StudySession)
}
