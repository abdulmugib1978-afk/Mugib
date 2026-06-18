package com.example.studytracker.ui.study

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.studytracker.data.local.AppDatabase
import com.example.studytracker.data.model.StudySession
import com.example.studytracker.data.repository.StudyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StudyUiState(
    val sessions: List<StudySession> = emptyList()
)

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = StudyRepository(db.studySessionDao())

    // Expose sessions as a StateFlow
    val sessionsState: StateFlow<List<StudySession>> = repo.sessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addSession(subjectName: String, durationInMinutes: Int) {
        viewModelScope.launch {
            repo.addSession(subjectName, durationInMinutes)
        }
    }

    fun deleteSession(session: StudySession) {
        viewModelScope.launch {
            repo.deleteSession(session)
        }
    }
}
