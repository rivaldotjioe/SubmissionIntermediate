package com.rivaldo.submissionintermediate.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.submissionintermediate.databinding.ListStoryBinding
import com.rivaldo.submissionintermediate.domain.model.StoryModel

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    val listStory : MutableList<StoryModel> = mutableListOf()
    lateinit var onItemClick: ((StoryModel) -> Unit)

    fun setListStory(listStory: List<StoryModel>) {
        this.listStory.clear()
        this.listStory.addAll(listStory)
        notifyDataSetChanged()
    }

    @JvmName("setOnItemClick1")
    fun setOnItemClick(onItemClick: (StoryModel) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class StoryViewHolder(val binding: ListStoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(storyModel: StoryModel){
            binding.tvItemName.text = storyModel.name
            Glide.with(itemView.context)
                .load(storyModel.photoUrl)
                .into(binding.ivItemPhoto)
            //bind photo
            itemView.setOnClickListener {
                if (::onItemClick.isInitialized){
                    onItemClick(storyModel)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }



}
