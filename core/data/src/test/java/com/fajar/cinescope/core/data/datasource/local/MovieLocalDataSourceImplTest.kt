package com.fajar.cinescope.core.data.datasource.local

import com.fajar.cinescope.core.data.datasource.local.implementation.MovieLocalDataSourceImpl
import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieDao
import com.fajar.cinescope.core.data.datasource.local.implementation.db.FavoriteMovieEntity
import com.fajar.cinescope.core.data.datasource.local.implementation.mapper.LocalDataMapperToDomain
import com.fajar.cinescope.core.test_utils.TestDummyFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("Unusedflow")
class MovieLocalDataSourceImplTest {

    private lateinit var dao: FavoriteMovieDao
    private lateinit var localDataSource: MovieLocalDataSource
    private val mapper = LocalDataMapperToDomain()
    private val testScheduler = TestCoroutineScheduler()

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        localDataSource = MovieLocalDataSourceImpl(
            favoriteMovieDao = dao,
            localDataMapperToDomain = mapper
        )
    }

    @After
    fun tearDown() {
        confirmVerified(dao)
    }

    @Test
    fun `Given movie detail When adding favorite movie Then inserts entity into DAO`() {
        runTest(testScheduler) {
            val movieDetail = TestDummyFactory.dummyMovieDetail
            val entity = FavoriteMovieEntity(
                id = movieDetail.id,
                title = movieDetail.title,
                imageUrl = movieDetail.imageUrl,
                overview = movieDetail.overview,
                relativeScore = movieDetail.rating?.normalizedRelativeScore,
                maxScore = movieDetail.rating?.maxScore,
                count = movieDetail.rating?.count
            )
            coEvery { dao.insertFavoriteMovie(entity) } returns Unit

            localDataSource.addFavoriteMovie(movieDetail)

            coVerify { dao.insertFavoriteMovie(entity) }
        }
    }

    @Test
    fun `Given favorite movie entities in DAO When getting favorite movies Then emits mapped favorite movie list`() {
        runTest(testScheduler) {
            val entity = FavoriteMovieEntity(
                id = 1L,
                title = "Test Movie",
                imageUrl = "https://image.com/poster.jpg",
                overview = "Overview",
                relativeScore = 8.5,
                maxScore = 10.0,
                count = 100
            )
            every { dao.getListFavoriteMovies() } returns flowOf(listOf(entity))

            val favoriteMovies = localDataSource.getListFavoriteMovies().first()

            assertEquals(1, favoriteMovies.size)
            assertEquals(entity.id, favoriteMovies[0].id)
            assertEquals(entity.title, favoriteMovies[0].title)
            verify { dao.getListFavoriteMovies() }
        }
    }

    @Test
    fun `Given movie id When deleting from list favorite Then deletes from DAO`() {
        runTest(testScheduler) {
            val movieId = 1L
            coEvery { dao.deleteFromListFavorite(movieId) } returns Unit

            localDataSource.deleteFromListFavorite(movieId)

            coVerify { dao.deleteFromListFavorite(movieId) }
        }
    }

    @Test
    fun `Given movie id When checking if movie in favorite list Then emits boolean status from DAO`() {
        runTest(testScheduler) {
            val movieId = 1L
            every { dao.checkIfMovieInFavoriteList(movieId) } returns flowOf(true)

            val isInFavorite = localDataSource.checkIsMovieInFavoriteList(movieId).first()

            assertEquals(true, isInFavorite)
            verify { dao.checkIfMovieInFavoriteList(movieId) }
        }
    }
}
