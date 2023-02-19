package com.rivaldo.submissionintermediate.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.submissionintermediate.databinding.ListStoryBinding

class StoryAdapter : PagingDataAdapter<StoryModel, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    lateinit var onItemClick: ((StoryModel, ActivityOptions) -> Unit)


    @JvmName("setOnItemClick1")
    fun setOnItemClick(onItemClick: (StoryModel, ActivityOptions) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class StoryViewHolder(val binding: ListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storyModel: StoryModel) {
            binding.tvItemName.text = storyModel.name
            Glide.with(itemView.context)
                .load(storyModel.photoUrl)
                .into(binding.ivItemPhoto)
            //bind photo
            itemView.setOnClickListener {
                if (::onItemClick.isInitialized) {
                    val optionsCompat: ActivityOptions =
                        ActivityOptions.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair.create(binding.ivItemPhoto as View, "photo"),
                            Pair.create(binding.tvItemName as View, "name")
                        )
                    onItemClick(storyModel, optionsCompat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}
