package com.fajar.cinescope.movie_previews.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.cinescope.core.common.DispatcherProvider
import com.fajar.cinescope.core.domain.model.DataError
import com.fajar.cinescope.core.domain.model.MovieCategory
import com.fajar.cinescope.core.domain.model.MoviePreviewsItemData
import com.fajar.cinescope.core.domain.model.fold
import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.ui.mapper.DefaultErrorMapper
import com.fajar.cinescope.core.ui.model.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed interface ListPreviewsDataState {
    data object Loading : ListPreviewsDataState
    data class Loaded(
        val previewsData: ImmutableList<MoviePreviewsItemData> = persistentListOf()
    ) : ListPreviewsDataState

    data class Error(
        val errorUIText: UIText? = null
    ) : ListPreviewsDataState
}

@Immutable
internal data class UIState(
    val popularMoviePreviewsDataState: ListPreviewsDataState = ListPreviewsDataState.Loading,
    val topRatedMoviePreviewsDataState: ListPreviewsDataState = ListPreviewsDataState.Loading,
    val nowPlayingMoviePreviewsDataState: ListPreviewsDataState = ListPreviewsDataState.Loading,
)

@HiltViewModel
internal class ListMoviePreviewsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val defaultErrorMapper: DefaultErrorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    fun refreshListPreviewsData(
        selectedMovieCategory: MovieCategory? = null
    ) {
        if (selectedMovieCategory == null) {
            loadPopularMoviePreviewsData()
            loadTopRatedMoviePreviewsData()
            loadNowPlayingMoviePreviewsData()
            return
        }

        when (selectedMovieCategory) {
            MovieCategory.POPULAR -> loadPopularMoviePreviewsData()
            MovieCategory.TOP_RATED -> loadTopRatedMoviePreviewsData()
            MovieCategory.NOW_PLAYING -> loadNowPlayingMoviePreviewsData()
        }
    }

    private var loadPopularMoviePreviewsDataJob: Job? = null
    private fun loadPopularMoviePreviewsData() {
        if (loadPopularMoviePreviewsDataJob?.isActive == true) return

        loadPopularMoviePreviewsDataJob = viewModelScope.launch(dispatcherProvider.default) {
            loadMoviePreviewsData(movieCategory = MovieCategory.POPULAR)
        }
    }

    private var loadTopRatedMoviePreviewsDataJob: Job? = null
    private fun loadTopRatedMoviePreviewsData() {
        if (loadTopRatedMoviePreviewsDataJob?.isActive == true) return

        loadTopRatedMoviePreviewsDataJob = viewModelScope.launch(dispatcherProvider.default) {
            loadMoviePreviewsData(movieCategory = MovieCategory.TOP_RATED)
        }
    }

    private var loadNowPlayingMoviePreviewsDataJob: Job? = null
    private fun loadNowPlayingMoviePreviewsData() {
        if (loadNowPlayingMoviePreviewsDataJob?.isActive == true) return

        loadNowPlayingMoviePreviewsDataJob = viewModelScope.launch(dispatcherProvider.default) {
            loadMoviePreviewsData(movieCategory = MovieCategory.NOW_PLAYING)
        }
    }

    private suspend fun loadMoviePreviewsData(movieCategory: MovieCategory) {
        updateListPreviewsState(
            state = ListPreviewsDataState.Loading,
            movieCategory = movieCategory
        )

        repository.getMoviePreviews(movieCategory)
            .fold(
                onSuccess = { listData ->
                    onSuccessLoadingMoviePreviewsHandler(
                        previewsData = listData,
                        movieCategory = movieCategory
                    )
                },
                onError = { error ->
                    onErrorLoadingMoviePreviewsHandler(error = error, movieCategory = movieCategory)
                }
            )
    }

    private fun onSuccessLoadingMoviePreviewsHandler(
        previewsData: List<MoviePreviewsItemData>,
        movieCategory: MovieCategory
    ) {
        val newListPreviewsState = ListPreviewsDataState
            .Loaded(previewsData = previewsData.toImmutableList())
        updateListPreviewsState(state = newListPreviewsState, movieCategory = movieCategory)
    }

    private fun onErrorLoadingMoviePreviewsHandler(
        error: DataError,
        movieCategory: MovieCategory
    ) {
        val errorUIText = defaultErrorMapper.getErrorStringResource(error)
        val newListPreviewsState = ListPreviewsDataState.Error(errorUIText)
        updateListPreviewsState(
            state = newListPreviewsState,
            movieCategory = movieCategory
        )
    }

    private fun updateListPreviewsState(
        state: ListPreviewsDataState,
        movieCategory: MovieCategory
    ) {
        when (movieCategory) {
            MovieCategory.POPULAR -> {
                _uiState.update {
                    it.copy(
                        popularMoviePreviewsDataState = state
                    )
                }
            }

            MovieCategory.TOP_RATED -> {
                _uiState.update {
                    it.copy(topRatedMoviePreviewsDataState = state)
                }
            }

            MovieCategory.NOW_PLAYING -> {
                _uiState.update {
                    it.copy(nowPlayingMoviePreviewsDataState = state)
                }
            }
        }
    }
}