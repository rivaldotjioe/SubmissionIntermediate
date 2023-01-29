package com.rivaldo.submissionintermediate.ui.addstory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityAddStoryBinding
import com.rivaldo.submissionintermediate.domain.model.StoryModel

class AddStoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}