package com.fajar.cinescope.core.data

import com.fajar.cinescope.core.data.datasource.local.MovieLocalDataSource
import com.fajar.cinescope.core.data.datasource.remote.MovieRemoteDataSource
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.test_utils.TestDummyFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("Unusedflow")
class MovieRepositoryImplTest {

    private lateinit var remoteDataSource: MovieRemoteDataSource
    private lateinit var localDataSource: MovieLocalDataSource
    private lateinit var repository: MovieRepository
    private val testScheduler = TestCoroutineScheduler()

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk(relaxed = true)
        repository = MovieRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @After
    fun tearDown() {
        confirmVerified(remoteDataSource, localDataSource)
    }

    @Test
    fun `Given remote data source succeeds When getting movie previews Then returns success result`() {
        runTest(testScheduler) {
            val expected = Result.Success(listOf(TestDummyFactory.dummyMoviePreviewsItem))
            coEvery { remoteDataSource.getMoviePreviews(MovieCategory.POPULAR) } returns expected

            val result = repository.getMoviePreviews(MovieCategory.POPULAR)

            assertEquals(expected, result)
            coVerify { remoteDataSource.getMoviePreviews(MovieCategory.POPULAR) }
        }
    }

    @Test
    fun `Given remote data source returns error When getting movie previews Then returns error result`() {
        runTest(testScheduler) {
            val expected = Result.Error(RootNetworkError.UNEXPECTED_ERROR)
            coEvery { remoteDataSource.getMoviePreviews(MovieCategory.POPULAR) } returns expected

            val result = repository.getMoviePreviews(MovieCategory.POPULAR)

            assertEquals(expected, result)
            coVerify { remoteDataSource.getMoviePreviews(MovieCategory.POPULAR) }
        }
    }

    @Test
    fun `Given remote data source succeeds When getting movie detail Then returns success result`() {
        runTest(testScheduler) {
            val movieId = 1L
            val expected = Result.Success(TestDummyFactory.dummyMovieDetail)
            coEvery { remoteDataSource.getMovieDetail(movieId) } returns expected

            val result = repository.getMovieDetail(movieId)

            assertEquals(expected, result)
            coVerify { remoteDataSource.getMovieDetail(movieId) }
        }
    }

    @Test
    fun `Given remote data source returns error When getting movie detail Then returns error result`() {
        runTest(testScheduler) {
            val movieId = 1L
            val expected = Result.Error(RootNetworkError.UNEXPECTED_ERROR)
            coEvery { remoteDataSource.getMovieDetail(movieId) } returns expected

            val result = repository.getMovieDetail(movieId)

            assertEquals(expected, result)
            coVerify { remoteDataSource.getMovieDetail(movieId) }
        }
    }

    @Test
    fun `Given local data source has movie When checking favorite status Then emits true`() {
        runTest(testScheduler) {
            val movieId = 1L
            every { localDataSource.checkIsMovieInFavoriteList(movieId) } returns flowOf(true)

            val isFavorite = repository.checkIsMovieInFavoriteList(movieId).first()

            assertEquals(true, isFavorite)
            verify { localDataSource.checkIsMovieInFavoriteList(movieId) }
        }
    }

    @Test
    fun `Given movie detail When adding favorite movie Then delegates to local data source`() {
        runTest(testScheduler) {
            val movieDetail = TestDummyFactory.dummyMovieDetail
            repository.addFavoriteMovie(movieDetail)

            coVerify { localDataSource.addFavoriteMovie(movieDetail) }
        }
    }

    @Test
    fun `Given movie id When deleting from favorites Then delegates to local data source`() {
        runTest(testScheduler) {
            val movieId = 1L
            repository.deleteFromListFavorite(movieId)

            coVerify { localDataSource.deleteFromListFavorite(movieId) }
        }
    }
}
