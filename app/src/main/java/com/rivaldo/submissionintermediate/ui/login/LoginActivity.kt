package com.rivaldo.submissionintermediate.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityLoginBinding
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.domain.model.LoginModel
import com.rivaldo.submissionintermediate.ui.main.MainActivity
import com.rivaldo.submissionintermediate.ui.register.RegisterActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            onLogin(onLoginSuccess = { onSuccessLogin(it) }, onLoading = { onLoading() }, onLoginFailed = { onFailedLogin(it) });
        }
    }

    private fun onLogin(onLoginSuccess: (LoginModel) -> Unit, onLoginFailed: (String) -> Unit, onLoading: () -> Unit) {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
        viewModel.viewModelScope.launch {
            viewModel.login(email = email, password = password).collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        onLoginSuccess(resource.data as LoginModel)
                    }
                    is Resource.Error -> {
                        onLoginFailed(resource.message.toString())
                    }
                    is Resource.Loading -> {
                        onLoading()
                    }
                }
            }
        }
    }

    private fun onSuccessLogin(loginModel: LoginModel) {
        viewModel.saveLoginData(loginModel)
        binding.progressBar2.visibility = android.view.View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onFailedLogin(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        binding.progressBar2.visibility = android.view.View.GONE
    }

    private fun onLoading() {
        binding.progressBar2.visibility = android.view.View.VISIBLE
    }
}