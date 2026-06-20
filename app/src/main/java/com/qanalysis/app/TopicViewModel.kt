package com.qanalysis.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.qanalysis.app.data.AppDatabase
import com.qanalysis.app.data.TopicEntity
import kotlinx.coroutines.launch

class TopicViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).topicDao()

    val allTopics: LiveData<List<TopicEntity>> = dao.getAll()

    fun add(subject: String, chapter: String, topic: String, count: Int) {
        viewModelScope.launch {
            dao.insert(
                TopicEntity(
                    subject = subject,
                    chapter = chapter,
                    topic = topic,
                    questionCount = count
                )
            )
        }
    }

    fun update(item: TopicEntity) {
        viewModelScope.launch { dao.update(item) }
    }

    fun delete(item: TopicEntity) {
        viewModelScope.launch { dao.delete(item) }
    }
}
