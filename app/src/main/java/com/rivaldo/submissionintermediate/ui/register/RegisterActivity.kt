package com.rivaldo.submissionintermediate.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.databinding.ActivityRegisterBinding
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            viewModel.viewModelScope.launch {
                viewModel.register(name, email, password).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Register Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity,
                                response.message ?: "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }
    }
}