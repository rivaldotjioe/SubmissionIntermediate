package com.rivaldo.submissionintermediate.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ListStoryBinding
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import de.hdodenhof.circleimageview.CircleImageView
import android.util.Pair

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    val listStory: MutableList<StoryModel> = mutableListOf()
    lateinit var onItemClick: ((StoryModel, ActivityOptions) -> Unit)

    fun setListStory(listStory: List<StoryModel>) {
        this.listStory.clear()
        this.listStory.addAll(listStory)
        notifyDataSetChanged()
    }

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

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }


}
