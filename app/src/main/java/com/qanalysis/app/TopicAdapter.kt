package com.qanalysis.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qanalysis.app.data.TopicEntity
import com.qanalysis.app.databinding.ItemTopicBinding

class TopicAdapter(
    private val onEdit: (TopicEntity) -> Unit,
    private val onDelete: (TopicEntity) -> Unit
) : ListAdapter<TopicEntity, TopicAdapter.TopicViewHolder>(DIFF_CALLBACK) {

    inner class TopicViewHolder(val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.binding.root.context
        holder.binding.tvSubject.text = context.getString(com.qanalysis.app.R.string.label_subject, item.subject)
        holder.binding.tvChapter.text = context.getString(com.qanalysis.app.R.string.label_chapter, item.chapter)
        holder.binding.tvTopic.text = context.getString(com.qanalysis.app.R.string.label_topic, item.topic)
        holder.binding.tvCount.text = context.getString(com.qanalysis.app.R.string.label_count, item.questionCount)

        holder.binding.btnEdit.setOnClickListener { onEdit(item) }
        holder.binding.btnDelete.setOnClickListener { onDelete(item) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopicEntity>() {
            override fun areItemsTheSame(oldItem: TopicEntity, newItem: TopicEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TopicEntity, newItem: TopicEntity): Boolean =
                oldItem == newItem
        }
    }
}
