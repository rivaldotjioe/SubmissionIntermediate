package com.rivaldo.submissionintermediate.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.rivaldo.core.domain.Resource
import com.rivaldo.submissionintermediate.databinding.ActivityRegisterBinding
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            lifecycleScope.launch {
                viewModel.register(name, email, password).collect { response ->
                    withContext(Dispatchers.Main) {
                        when (response) {
                            is Resource.Success -> {
                                Toast.makeText(
                                    applicationContext,
                                    "Register Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Intent(applicationContext, LoginActivity::class.java).also {
                                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(it)
                                }
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.viewModelScope.cancel()
        lifecycleScope.cancel()
    }
}