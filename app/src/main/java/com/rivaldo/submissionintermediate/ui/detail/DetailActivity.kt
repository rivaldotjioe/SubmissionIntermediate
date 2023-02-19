package com.rivaldo.submissionintermediate.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.submissionintermediate.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val storyModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, StoryModel::class.java)
        } else {
            intent.getParcelableExtra<StoryModel>(EXTRA_DATA)
        }
        binding.tvDetailName.text = storyModel?.name
        binding.tvDetailDescription.text = storyModel?.description
        Glide.with(this)
            .load(storyModel?.photoUrl)
            .into(binding.ivDetailPhoto)
    }

    companion object {
        val EXTRA_DATA: String = "extra_story_model"
    }
}