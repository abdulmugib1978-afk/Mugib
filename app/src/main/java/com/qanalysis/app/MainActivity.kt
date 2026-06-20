package com.qanalysis.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qanalysis.app.data.TopicEntity
import com.qanalysis.app.databinding.ActivityMainBinding
import com.qanalysis.app.databinding.DialogAddEditBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TopicViewModel
    private lateinit var adapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TopicViewModel::class.java]

        adapter = TopicAdapter(
            onEdit = { showAddEditDialog(it) },
            onDelete = { item -> confirmDelete(item) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allTopics.observe(this) { list ->
            adapter.submitList(list)
            binding.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.fabAdd.setOnClickListener { showAddEditDialog(null) }
    }

    private fun confirmDelete(item: TopicEntity) {
        AlertDialog.Builder(this)
            .setTitle("ডিলিট করবেন?")
            .setMessage("\"${item.topic}\" টপিকটি ডিলিট করতে চান?")
            .setPositiveButton("ডিলিট") { _, _ -> viewModel.delete(item) }
            .setNegativeButton("বাতিল", null)
            .show()
    }

    private fun showAddEditDialog(existing: TopicEntity?) {
        val dialogBinding = DialogAddEditBinding.inflate(layoutInflater)

        existing?.let {
            dialogBinding.etSubject.setText(it.subject)
            dialogBinding.etChapter.setText(it.chapter)
            dialogBinding.etTopic.setText(it.topic)
            dialogBinding.etCount.setText(it.questionCount.toString())
        }

        AlertDialog.Builder(this)
            .setTitle(if (existing == null) "নতুন টপিক যুক্ত করুন" else "টপিক সম্পাদনা করুন")
            .setView(dialogBinding.root)
            .setPositiveButton("সেভ") { _, _ ->
                val subject = dialogBinding.etSubject.text.toString().trim()
                val chapter = dialogBinding.etChapter.text.toString().trim()
                val topic = dialogBinding.etTopic.text.toString().trim()
                val countText = dialogBinding.etCount.text.toString().trim()

                if (subject.isEmpty() || chapter.isEmpty() || topic.isEmpty() || countText.isEmpty()) {
                    Toast.makeText(this, "সব ফিল্ড পূরণ করুন", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val count = countText.toIntOrNull() ?: 0

                if (existing == null) {
                    viewModel.add(subject, chapter, topic, count)
                } else {
                    viewModel.update(
                        existing.copy(
                            subject = subject,
                            chapter = chapter,
                            topic = topic,
                            questionCount = count
                        )
                    )
                }
            }
            .setNegativeButton("বাতিল", null)
            .show()
    }
}
