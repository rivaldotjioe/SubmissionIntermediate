package com.rivaldo.submissionintermediate.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.core.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityMainBinding
import com.rivaldo.submissionintermediate.ui.addstory.AddStoryActivity
import com.rivaldo.submissionintermediate.ui.detail.DetailActivity
import com.rivaldo.submissionintermediate.ui.login.LoginActivity
import com.rivaldo.submissionintermediate.ui.maps.ListStoryMapsActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _menuProvider: MenuProvider? = null
    private val menuProvider get() = _menuProvider!!
    private val viewModel: MainViewModel by viewModel()
    private val rvAdapter: StoryAdapter = StoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        rvAdapter.setOnItemClick { storyModel, listPairView ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, storyModel)
            val optionsCompat: ActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity,
                    *listPairView.toTypedArray()
                )
            startActivity(
                intent,
                optionsCompat.toBundle()
            )
        }
    }

    private fun setupMenu(){
        _menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.main_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.add_story -> {
                        val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
                        startActivity(intent)
                        return true
                    }
                    R.id.logout -> {
                        viewModel.logout()
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        return true
                    }
                    R.id.map -> {
                        val intent = Intent(this@MainActivity, ListStoryMapsActivity::class.java)
                        startActivity(intent)
                        return true
                    }

                    R.id.favorite_list -> {
                        val intent = Intent(
                            this@MainActivity,
                            Class.forName("com.rivaldo.favorite.FavoriteActivity")
                        )
                        startActivity(intent)
                        return true
                    }
                    else -> return false
                }
            }
        }
        (this as MenuHost).addMenuProvider(menuProvider)
    }

    private fun getListStory() {
       lifecycleScope.launch(Dispatchers.IO) {
            viewModel.storiesFlow.collect {
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



    override fun onDestroy() {
        super.onDestroy()
        (this as MenuHost).removeMenuProvider(menuProvider)
        lifecycleScope.cancel()
        lifecycleScope.coroutineContext.cancel()
        lifecycleScope.coroutineContext.cancelChildren()
        System.gc()
    }





}