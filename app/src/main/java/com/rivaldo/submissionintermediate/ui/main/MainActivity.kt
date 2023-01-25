package com.rivaldo.submissionintermediate.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityMainBinding
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryActivity
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import com.rivaldo.submissionintermediate.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}