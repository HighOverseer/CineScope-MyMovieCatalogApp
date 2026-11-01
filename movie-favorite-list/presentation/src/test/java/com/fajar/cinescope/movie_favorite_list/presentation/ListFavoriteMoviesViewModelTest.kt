package com.fajar.cinescope.movie_favorite_list.presentation

import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.test_utils.TestDispatcherProvider
import com.fajar.cinescope.core.test_utils.TestDummyFactory
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("Unusedflow")
class ListFavoriteMoviesViewModelTest {

    private lateinit var repository: MovieRepository
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcherProvider = TestDispatcherProvider(testScheduler)

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
    fun `Given repository emits favorite movies When observing favorite list Then emits populated list`() {
        runTest(testScheduler) {
            val dummyList = listOf(TestDummyFactory.dummyFavoriteMovieItem)
            every { repository.getListFavoriteMovies() } returns flowOf(dummyList)

            val viewModel = ListFavoriteMoviesViewModel(repository)
            val job = backgroundScope.launch(dispatcherProvider.main) {
                viewModel.favoriteMovies.collect {}
            }
            testScheduler.runCurrent()

            assertEquals(1, viewModel.favoriteMovies.value.size)
            assertEquals(TestDummyFactory.dummyFavoriteMovieItem.id, viewModel.favoriteMovies.value[0].id)
            job.cancel()
            verify { repository.getListFavoriteMovies() }
        }
    }

    @Test
    fun `Given repository emits empty list When observing favorite list Then emits empty list`() {
        runTest(testScheduler) {
            every { repository.getListFavoriteMovies() } returns flowOf(emptyList())

            val viewModel = ListFavoriteMoviesViewModel(repository)
            val job = backgroundScope.launch(dispatcherProvider.main) {
                viewModel.favoriteMovies.collect {}
            }
            testScheduler.runCurrent()

            assertEquals(0, viewModel.favoriteMovies.value.size)
            job.cancel()
            verify { repository.getListFavoriteMovies() }
        }
    }
}
