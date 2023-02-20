package com.rivaldo.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldo.core.domain.Resource
import com.rivaldo.favorite.databinding.ActivityFavoriteBinding
import com.rivaldo.favorite.di.favoriteModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()
    val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        getAllStoriesFavorite()
    }

    private fun setupRecyclerView() {
        binding.listFavoriteStory.adapter = adapter
        binding.listFavoriteStory.layoutManager = LinearLayoutManager(this)
    }

    private fun getAllStoriesFavorite() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAllStoriesFavorite().collect {
                when(it) {
                    is Resource.Success -> {
                        it.data?.let { stories ->
                            withContext(Dispatchers.Main) {
                                adapter.setListStoryModel(stories)
                            }
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { message ->

                        }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }

    }

}