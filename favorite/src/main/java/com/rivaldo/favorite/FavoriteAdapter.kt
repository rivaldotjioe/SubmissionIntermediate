package com.rivaldo.favorite

import android.app.Activity
import android.app.ActivityOptions
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.submissionintermediate.databinding.ListStoryBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.StoryViewHolder>() {
    lateinit var onItemClick: ((StoryModel, ActivityOptions) -> Unit)
    var listStoryModel = mutableListOf<StoryModel>()

    @JvmName("setOnItemClick1")
    fun setOnItemClick(onItemClick: (StoryModel, ActivityOptions) -> Unit) {
        this.onItemClick = onItemClick
    }
    @JvmName("setListStoryModel1")
    fun setListStoryModel(listStoryModel: List<StoryModel>) {
        val diffCallback = FavoriteDiffCallback(this.listStoryModel, listStoryModel)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listStoryModel.clear()
        this.listStoryModel.addAll(listStoryModel)
        diffResult.dispatchUpdatesTo(this)
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

    override fun getItemCount(): Int = listStoryModel.size

    override fun onBindViewHolder(holder: FavoriteAdapter.StoryViewHolder, position: Int) {
        holder.bind(listStoryModel[position])
    }


}
