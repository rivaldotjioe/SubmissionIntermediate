package com.rivaldo.submissionintermediate.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityMainBinding
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryActivity
import com.rivaldo.submissionintermediate.ui.detail.DetailActivity
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import com.rivaldo.submissionintermediate.ui.maps.ListStoryMapsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val rvAdapter: StoryAdapter by lazy { StoryAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
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
            layoutManager = linearLayoutManager
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
                    R.id.map -> {
                        val intent = Intent(this@MainActivity, ListStoryMapsActivity::class.java )
                        startActivity(intent)
                        return true
                    }

                    R.id.favorite_list -> {
                        val intent = Intent(this@MainActivity, Class.forName("com.rivaldo.favorite.FavoriteActivity"))
                        startActivity(intent)
                        return true
                    }
                    else -> false
                }
            }
        })
    }

    private fun getListStory() {
       lifecycleScope.launch(Dispatchers.IO) {
            viewModel.storiesFlow.collectLatest {
                runOnUiThread {
                    rvAdapter.submitData(lifecycle, it)
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        rvAdapter.refresh()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(200)
            binding.rvStory.layoutManager?.scrollToPosition(0)
        }

    }
    override fun onPause() {
        super.onPause()
    }

    private fun checkIsLogin() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.checkIsLogin().collect { isLoggedIn ->
                if (!isLoggedIn) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}