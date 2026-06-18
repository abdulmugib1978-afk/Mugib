package com.example.studytracker.data.repository

import com.example.studytracker.data.local.StudySessionDao
import com.example.studytracker.data.model.StudySession
import kotlinx.coroutines.flow.Flow

class StudyRepository(
    private val dao: StudySessionDao
) {
    val sessions: Flow<List<StudySession>> = dao.getAllSessions()

    suspend fun addSession(subjectName: String, durationInMinutes: Int) {
        val session = StudySession(
            subjectName = subjectName,
            durationInMinutes = durationInMinutes,
            timestamp = System.currentTimeMillis()
        )
        dao.insert(session)
    }

    suspend fun deleteSession(session: StudySession) {
        dao.delete(session)
    }
}
