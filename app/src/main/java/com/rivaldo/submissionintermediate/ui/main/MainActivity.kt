package com.rivaldo.submissionintermediate.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityMainBinding
import com.rivaldo.submissionintermediate.domain.Resource
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryActivity
import com.rivaldo.submissionintermediate.ui.detail.DetailActivity
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import com.rivaldo.submissionintermediate.ui.register.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val rvAdapter: StoryAdapter by lazy { StoryAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runBlocking { checkIsLogin() }
        initializeRecyclerView()
        getListStory()
    }

    private fun initializeRecyclerView() {
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            this.adapter = rvAdapter
        }

        rvAdapter.setOnItemClick { storyModel ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, storyModel)
            startActivity(intent) }
    }

    private fun getListStory() {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            viewModel.getListStory().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar3.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar3.visibility = View.GONE
                        resource.data?.let {
                            runOnUiThread { rvAdapter.setListStory(it) }

                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar3.visibility = View.GONE
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            }
        }
    }

    private fun checkIsLogin() {
        viewModel.viewModelScope.launch {
            viewModel.checkIsLogin().collect { isLoggedIn ->
                if (!isLoggedIn) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}