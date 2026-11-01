package com.fajar.cinescope.core.data.datasource.remote

import com.fajar.cinescope.core.data.datasource.remote.implementations.MovieRemoteDataSourceImpl
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.MovieAPIService
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetListMoviePreviewsResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieOverviewResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.GetMovieReviewsResponseDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.dto.MoviePreviewsResponseItemDTO
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.MovieImageUrlResolver
import com.fajar.cinescope.core.data.datasource.remote.implementations.network.mapper.RemoteDataMapperToDomain
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.Result
import com.fajar.cinescope.core.domain.model.RootNetworkError
import com.fajar.cinescope.core.test_utils.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRemoteDataSourceImplTest {

    private lateinit var apiService: MovieAPIService
    private lateinit var remoteDataSource: MovieRemoteDataSource
    private val testScheduler = TestCoroutineScheduler()
    private val dispatcherProvider = TestDispatcherProvider(testScheduler)
    private val imageResolver = MovieImageUrlResolver("https://image.org/")
    private val mapper = RemoteDataMapperToDomain(imageResolver)

    @Before
    fun setUp() {
        apiService = mockk()
        remoteDataSource = MovieRemoteDataSourceImpl(
            apiService = apiService,
            dispatcherProvider = dispatcherProvider,
            remoteDataMapperToDomain = mapper
        )
    }

    @After
    fun tearDown() {
        confirmVerified(apiService)
    }

    @Test
    fun `Given popular movie previews API succeeds When requesting popular previews Then returns success result with mapped items`() {
        runTest(testScheduler) {
            val itemDto = MoviePreviewsResponseItemDTO(
                id = 1,
                title = "Test Movie",
                backdropPath = "/backdrop.jpg",
                voteAverage = 8.5,
                voteCount = 100
            )
            val responseDto = GetListMoviePreviewsResponseDTO(results = listOf(itemDto))
            coEvery { apiService.getPopularMoviePreviews() } returns responseDto

            val result = remoteDataSource.getMoviePreviews(MovieCategory.POPULAR)

            val expectedItem = mapper.mapToMoviePreviewItemData(itemDto, false)!!
            assertEquals(Result.Success(listOf(expectedItem)), result)
            coVerify { apiService.getPopularMoviePreviews() }
        }
    }

    @Test
    fun `Given popular movie previews API returns null results When requesting popular previews Then returns unexpected error`() {
        runTest(testScheduler) {
            val responseDto = GetListMoviePreviewsResponseDTO(results = null)
            coEvery { apiService.getPopularMoviePreviews() } returns responseDto

            val result = remoteDataSource.getMoviePreviews(MovieCategory.POPULAR)

            assertEquals(Result.Error(RootNetworkError.UNEXPECTED_ERROR), result)
            coVerify { apiService.getPopularMoviePreviews() }
        }
    }

    @Test
    fun `Given popular movie previews API throws exception When requesting popular previews Then returns error result`() {
        runTest(testScheduler) {
            coEvery { apiService.getPopularMoviePreviews() } throws IOException("Network error")

            val result = remoteDataSource.getMoviePreviews(MovieCategory.POPULAR)

            assertEquals(Result.Error(RootNetworkError.UNEXPECTED_ERROR), result)
            coVerify { apiService.getPopularMoviePreviews() }
        }
    }

    @Test
    fun `Given movie overview and review APIs succeed When requesting movie detail Then returns success result with mapped detail`() {
        runTest(testScheduler) {
            val movieId = 1L
            val overviewDto = GetMovieOverviewResponseDTO(
                id = 1,
                title = "Movie 1",
                backdropPath = "/backdrop.jpg",
                overview = "Overview",
                voteAverage = 8.0,
                voteCount = 50
            )
            val reviewsDto = GetMovieReviewsResponseDTO(results = emptyList())

            coEvery { apiService.getSelectedMovieOverview(movieId) } returns overviewDto
            coEvery { apiService.getSelectedMovieReview(movieId) } returns reviewsDto

            val result = remoteDataSource.getMovieDetail(movieId)

            val expectedDetail = mapper.mapToMovieDetail(overviewDto, reviewsDto)!!
            assertEquals(Result.Success(expectedDetail), result)
            coVerify {
                apiService.getSelectedMovieOverview(movieId)
                apiService.getSelectedMovieReview(movieId)
            }
        }
    }

    @Test
    fun `Given movie overview and review APIs return invalid data When requesting movie detail Then returns error result`() {
        runTest(testScheduler) {
            val movieId = 1L
            val overviewDto = GetMovieOverviewResponseDTO(
                id = 1,
                title = "Movie 1",
                backdropPath = null,
                overview = "Overview",
                voteAverage = 8.0,
                voteCount = 50
            )
            val reviewsDto = GetMovieReviewsResponseDTO(results = emptyList())

            coEvery { apiService.getSelectedMovieOverview(movieId) } returns overviewDto
            coEvery { apiService.getSelectedMovieReview(movieId) } returns reviewsDto

            val result = remoteDataSource.getMovieDetail(movieId)

            assertEquals(Result.Error(RootNetworkError.UNEXPECTED_ERROR), result)
            coVerify {
                apiService.getSelectedMovieOverview(movieId)
                apiService.getSelectedMovieReview(movieId)
            }
        }
    }
}
