package com.example.studytracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_sessions")
data class StudySession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectName: String,
    val durationInMinutes: Int,
    val timestamp: Long
)
