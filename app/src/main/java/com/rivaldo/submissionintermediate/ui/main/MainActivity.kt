package com.rivaldo.submissionintermediate.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
        setupMenu()
    }

    private fun initializeRecyclerView() {
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            this.adapter = rvAdapter
        }

        rvAdapter.setOnItemClick { storyModel, activityOptionsCompat ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, storyModel)
            startActivity(
                intent,
                activityOptionsCompat.toBundle()
            )
        }
    }

    private fun setupMenu(){
        (this as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_story -> {
                        val intent = Intent(this@MainActivity, AddStoryActivity::class.java )
                        startActivity(intent)
                        return true
                    }
                    R.id.logout -> {
                        viewModel.logout()
                        return true
                    }
                    else -> false
                }
            }
        })
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