package com.fajar.cinescope.movie_detail.presentation

import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.test_utils.TestDispatcherProvider
import com.fajar.cinescope.core.test_utils.TestDummyFactory
import com.fajar.cinescope.core.ui.mapper.DefaultErrorMapper
import com.fajar.cinescope.core.ui.utils.formatter.DefaultDUIDateFormatter
import com.fajar.cinescope.movie_detail.presentation.mapper.DUIMapper
import com.fajar.cinescope.movie_detail.presentation.model.MovieDetailDUI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("Unusedflow")
class MovieDetailViewModelTest {

    private lateinit var repository: MovieRepository
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcherProvider = TestDispatcherProvider(testScheduler)
    private val errorMapper = DefaultErrorMapper()
    private val dateFormatter = DefaultDUIDateFormatter()
    private val duiMapper = DUIMapper(dateFormatter)
    private lateinit var viewModel: MovieDetailViewModel

    private val movieId = 1L

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcherProvider.main)
        repository = mockk(relaxed = true)
        viewModel = MovieDetailViewModel(
            repository = repository,
            duiMapper = duiMapper,
            dispatcherProvider = dispatcherProvider,
            defaultErrorMapper = errorMapper,
            selectedMovieId = movieId
        )
    }

    @After
    fun tearDown() {
        confirmVerified(repository)
    }

    @Test
    fun `Given repository succeeds When initializing movie detail Then updates UI state with movie details`() {
        runTest(testScheduler) {
            val movieId = 1L
            val movieDetail = TestDummyFactory.dummyMovieDetail
            coEvery { repository.getMovieDetail(movieId) } returns Result.Success(movieDetail)
            every { repository.checkIsMovieInFavoriteList(movieId) } returns flowOf(false)

            val expectedState = MovieDetailUIState(
                isLoading = false,
                movieDetailDUI = duiMapper.mapDetailToDUI(movieDetail),
                errorUserMessage = null,
                isFavorite = false
            )

            viewModel.initializeIfNeeded()
            testScheduler.advanceUntilIdle()

            assertEquals(expectedState, viewModel.uiState.value)
            coVerify(exactly = 1) { repository.getMovieDetail(movieId) }
            verify(exactly = 1) { repository.checkIsMovieInFavoriteList(movieId) }
        }
    }

    @Test
    fun `Given repository returns error When initializing movie detail Then updates UI state with error message`() {
        runTest(testScheduler) {
            val movieId = 1L
            val error = RootNetworkError.UNEXPECTED_ERROR
            coEvery { repository.getMovieDetail(movieId) } returns Result.Error(error)
            every { repository.checkIsMovieInFavoriteList(movieId) } returns flowOf(false)

            val expectedState = MovieDetailUIState(
                isLoading = false,
                movieDetailDUI = MovieDetailDUI(),
                errorUserMessage = errorMapper.getErrorStringResource(error),
                isFavorite = false
            )

            viewModel.initializeIfNeeded()
            testScheduler.advanceUntilIdle()

            assertEquals(expectedState, viewModel.uiState.value)
            coVerify(exactly = 1) { repository.getMovieDetail(movieId) }
            verify(exactly = 1) { repository.checkIsMovieInFavoriteList(movieId) }
        }
    }

    @Test
    fun `Given movie is not favorite When toggling favorite status Then adds movie to favorites and keeps expected DUI`() {
        runTest(testScheduler) {
            val movieId = 1L
            val movieDetail = TestDummyFactory.dummyMovieDetail
            val expectedDUI = duiMapper.mapDetailToDUI(movieDetail)
            coEvery { repository.getMovieDetail(movieId) } returns Result.Success(movieDetail)
            every { repository.checkIsMovieInFavoriteList(movieId) } returns flowOf(false)

            viewModel.initializeIfNeeded()
            testScheduler.advanceUntilIdle()

            viewModel.toggleFavoriteStatus()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(expectedDUI, state.movieDetailDUI)
            assertEquals(false, state.isLoading)
            coVerify(exactly = 1) { repository.getMovieDetail(movieId) }
            verify(exactly = 1) { repository.checkIsMovieInFavoriteList(movieId) }
            coVerify(exactly = 1) { repository.addFavoriteMovie(movieDetailData = movieDetail) }
            coVerify(exactly = 0) { repository.deleteFromListFavorite(any()) }
        }
    }

    @Test
    fun `Given movie is already favorite When toggling favorite status Then removes movie from favorites and keeps expected DUI`() {
        runTest(testScheduler) {
            val movieId = 1L
            val movieDetail = TestDummyFactory.dummyMovieDetail
            val expectedDUI = duiMapper.mapDetailToDUI(movieDetail)
            coEvery { repository.getMovieDetail(movieId) } returns Result.Success(movieDetail)
            every { repository.checkIsMovieInFavoriteList(movieId) } returns flowOf(true)

            viewModel.initializeIfNeeded()
            testScheduler.advanceUntilIdle()

            assertEquals(true, viewModel.uiState.value.isFavorite)

            viewModel.toggleFavoriteStatus()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(expectedDUI, state.movieDetailDUI)
            coVerify(exactly = 1) { repository.getMovieDetail(movieId) }
            verify(exactly = 1) { repository.checkIsMovieInFavoriteList(movieId) }
            coVerify(exactly = 1) { repository.deleteFromListFavorite(movieId) }
            coVerify(exactly = 0) { repository.addFavoriteMovie(any()) }
        }
    }

    @Test
    fun `Given movie detail failed to load When toggling favorite status Then does nothing`() {
        runTest(testScheduler) {
            val movieId = 1L
            coEvery { repository.getMovieDetail(movieId) } returns Result.Error(RootNetworkError.UNEXPECTED_ERROR)
            every { repository.checkIsMovieInFavoriteList(movieId) } returns flowOf(false)

            viewModel.initializeIfNeeded()
            testScheduler.advanceUntilIdle()

            viewModel.toggleFavoriteStatus()
            testScheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(MovieDetailDUI(), state.movieDetailDUI)
            coVerify(exactly = 1) { repository.getMovieDetail(movieId) }
            verify(exactly = 1) { repository.checkIsMovieInFavoriteList(movieId) }
            coVerify(exactly = 0) { repository.addFavoriteMovie(any()) }
            coVerify(exactly = 0) { repository.deleteFromListFavorite(any()) }
        }
    }
}
