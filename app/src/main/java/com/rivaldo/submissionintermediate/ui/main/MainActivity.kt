package com.rivaldo.submissionintermediate.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}