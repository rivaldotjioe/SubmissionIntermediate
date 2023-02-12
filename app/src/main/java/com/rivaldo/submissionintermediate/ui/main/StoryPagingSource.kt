package com.rivaldo.submissionintermediate.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.data.remote.ApiResponse
import com.rivaldo.submissionintermediate.data.remote.api.RemoteDataSource
import com.rivaldo.submissionintermediate.data.remote.model.ListStoryItem
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.utils.DataMapper.toStoryModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class StoryPagingSource(private val remoteDataSource: RemoteDataSource, private val preferences: DataStorePreferences) : PagingSource<Int, StoryModel>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = preferences.getToken().first()
            var listStory = listOf<StoryModel>()
            remoteDataSource.getAllStoriesPaging(token = token, page = position, size = params.loadSize).collect { response ->
                when(response) {
                    is  ApiResponse.Success -> {
                        response.data?.listStory.let {
                            if (it != null && it.isNotEmpty()) {
                                val responseListStory = response.data?.listStory as List<ListStoryItem>
                                listStory = responseListStory.toStoryModel()
                            }
                        }

                    }
                    is ApiResponse.Error -> {

                    }
                    is ApiResponse.Loading -> {

                    }
                }
            }
            LoadResult.Page(
                data = listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}