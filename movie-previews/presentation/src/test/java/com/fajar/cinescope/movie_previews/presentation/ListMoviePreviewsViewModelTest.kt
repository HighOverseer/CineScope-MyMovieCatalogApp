package com.fajar.cinescope.movie_previews.presentation

import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.test_utils.TestDispatcherProvider
import com.fajar.cinescope.core.test_utils.TestDummyFactory
import com.fajar.cinescope.core.ui.mapper.DefaultErrorMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ListMoviePreviewsViewModelTest {

    private lateinit var repository: MovieRepository
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcherProvider = TestDispatcherProvider(testScheduler)
    private val errorMapper = DefaultErrorMapper()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcherProvider.main)
        repository = mockk()
    }

    @After
    fun tearDown() {
        confirmVerified(repository)
    }

    @Test
    fun `Given repository succeeds When refreshing previews Then updates UI state with loaded previews`() {
        runTest(testScheduler) {
            val expectedPopularPreviews = listOf(TestDummyFactory.dummyMoviePreviewsItem)
            val expectedTopRatedPreviews = listOf<MoviePreviewsItemData>()
            val expectedNowPlayingPreviews = listOf(TestDummyFactory.dummyMoviePreviewsItem)
            coEvery { repository.getMoviePreviews(MovieCategory.POPULAR) } returns Result.Success(expectedPopularPreviews)
            coEvery { repository.getMoviePreviews(MovieCategory.TOP_RATED) } returns Result.Success(expectedTopRatedPreviews)
            coEvery { repository.getMoviePreviews(MovieCategory.NOW_PLAYING) } returns Result.Success(expectedNowPlayingPreviews)

            val viewModel = ListMoviePreviewsViewModel(
                repository = repository,
                dispatcherProvider = dispatcherProvider,
                defaultErrorMapper = errorMapper
            )
            viewModel.refreshListPreviewsData()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(state.popularMoviePreviewsDataState, ListPreviewsDataState.Loaded(expectedPopularPreviews.toImmutableList()))
            assertEquals(state.topRatedMoviePreviewsDataState, ListPreviewsDataState.Loaded(expectedTopRatedPreviews.toImmutableList()))
            assertEquals(state.nowPlayingMoviePreviewsDataState, ListPreviewsDataState.Loaded(expectedNowPlayingPreviews.toImmutableList()))
            coVerify {
                repository.getMoviePreviews(MovieCategory.POPULAR)
                repository.getMoviePreviews(MovieCategory.TOP_RATED)
                repository.getMoviePreviews(MovieCategory.NOW_PLAYING)
            }
        }
    }

    @Test
    fun `Given repository returns error When refreshing previews Then updates UI state with error state`() {
        runTest(testScheduler) {
            val expectedError = RootNetworkError.UNEXPECTED_ERROR
            coEvery { repository.getMoviePreviews(MovieCategory.POPULAR) } returns Result.Error(expectedError)
            coEvery { repository.getMoviePreviews(MovieCategory.TOP_RATED) } returns Result.Error(expectedError)
            coEvery { repository.getMoviePreviews(MovieCategory.NOW_PLAYING) } returns Result.Error(expectedError)

            val viewModel = ListMoviePreviewsViewModel(
                repository = repository,
                dispatcherProvider = dispatcherProvider,
                defaultErrorMapper = errorMapper
            )
            viewModel.refreshListPreviewsData()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            val errorUIText = errorMapper.getErrorStringResource(expectedError)
            assertEquals(state.popularMoviePreviewsDataState, ListPreviewsDataState.Error(errorUIText))
            assertEquals(state.topRatedMoviePreviewsDataState, ListPreviewsDataState.Error(errorUIText))
            assertEquals(state.nowPlayingMoviePreviewsDataState, ListPreviewsDataState.Error(errorUIText))
            coVerify {
                repository.getMoviePreviews(MovieCategory.POPULAR)
                repository.getMoviePreviews(MovieCategory.TOP_RATED)
                repository.getMoviePreviews(MovieCategory.NOW_PLAYING)
            }
        }
    }
}
