package com.rivaldo.submissionintermediate.ui.main

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.rivaldo.submissionintermediate.data.local.DataStorePreferences
import com.rivaldo.submissionintermediate.data.remote.repository.StoriesRepository
import com.rivaldo.submissionintermediate.domain.interactor.HomeListStoryInteractor
import com.rivaldo.submissionintermediate.domain.model.StoryModel
import com.rivaldo.submissionintermediate.domain.repoInterface.IStoriesRepository
import com.rivaldo.submissionintermediate.domain.useCase.HomeListStoryUseCase
import com.rivaldo.submissionintermediate.utils.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest{

    @Mock
    private lateinit var storiesRepository: IStoriesRepository
    @Mock
    private lateinit var preferences: DataStorePreferences
    private lateinit var mainViewModel : MainViewModel
    private lateinit var useCase : HomeListStoryUseCase
    private val dummyStory = DataDummy.generateDummyStoryEntity()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        storiesRepository = mock(StoriesRepository::class.java)
        preferences = mock(DataStorePreferences::class.java)
        useCase = HomeListStoryInteractor(storiesRepository, preferences)
        mainViewModel = MainViewModel(useCase)
        `when`(preferences.getToken()).thenReturn(flow {
            emit("token")
        })
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Get Stories Paging Should not null and return the Data`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val data : PagingData<StoryModel> = StoryPagingSource.snapshot(dummyStory)
        val expectedStory = flow {
            emit(data)
        }
        `when`(storiesRepository.getAllStoriesPaging()).thenReturn(expectedStory)
        val actualStory : PagingData<StoryModel> = mainViewModel.storiesFlow.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
        assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)
        assertEquals(dummyStory[0].description, differ.snapshot()[0]?.description)
        assertEquals(dummyStory[0].photoUrl, differ.snapshot()[0]?.photoUrl)
        assertEquals(dummyStory[0].lat, differ.snapshot()[0]?.lat)
        assertEquals(dummyStory[0].lon, differ.snapshot()[0]?.lon)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Get Stories Paging null`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val dataNull : PagingData<StoryModel> = StoryPagingSource.snapshot(emptyList())
        val expectedStory = flow {
            emit(dataNull)
        }
        `when`(storiesRepository.getAllStoriesPaging()).thenReturn(expectedStory)
        val actualStory : PagingData<StoryModel> = mainViewModel.storiesFlow.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(emptyList<StoryModel>(), differ.snapshot())
        assertEquals(0, differ.snapshot().size)
    }
}

class StoryPagingSource : PagingSource<Int, StoryModel>() {

    companion object {
        fun snapshot(items: List<StoryModel>) : PagingData<StoryModel> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return LoadResult.Page(
            data = emptyList(),
            prevKey = 0,
            nextKey = 1
        )
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}