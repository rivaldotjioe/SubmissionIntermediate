package com.rivaldo.submissionintermediate.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rivaldo.core.domain.model.StoryModel
import com.rivaldo.submissionintermediate.R
import com.rivaldo.submissionintermediate.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    var storyModel: StoryModel? = null
    var isFavoriteStory by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storyModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, StoryModel::class.java)
        } else {
            intent.getParcelableExtra<StoryModel>(EXTRA_DATA)
        }
        setupMenu()
        binding.tvDetailName.text = storyModel?.name
        binding.tvDetailDescription.text = storyModel?.description
        Glide.with(this)
            .load(storyModel?.photoUrl)
            .into(binding.ivDetailPhoto)

    }




    private fun setupMenu() {
        (this as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                // set icon by are this story has been added to dataabase or not
//                menu.findItem(R.id.add_favorite).
                if (storyModel != null && storyModel?.id != null) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.isFavorite.collect { isFavorite ->
                            this@DetailActivity.isFavoriteStory = isFavorite
                            if (isFavorite) {
                                menu.findItem(R.id.add_favorite).setIcon(R.drawable.baseline_favorite_24)
                            } else {
                                menu.findItem(R.id.add_favorite).setIcon(R.drawable.baseline_favorite_border_24)
                            }
                        }
                    }
                }
                viewModel.getIsFavorite(storyModel?.id!!)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.add_favorite -> {
                        if (storyModel != null && storyModel?.id != null) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                if (isFavoriteStory) {
                                    viewModel.deleteFavorite(storyModel!!)
                                } else {
                                    viewModel.addToFavorite(storyModel!!)
                                }
                            }
                        }
                    }
                }
                return true
            }
        })
    }

    companion object {
        val EXTRA_DATA: String = "extra_story_model"
    }
}