package com.rivaldo.submissionintermediate.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityLoginBinding
import com.rivaldo.submissionintermediate.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}