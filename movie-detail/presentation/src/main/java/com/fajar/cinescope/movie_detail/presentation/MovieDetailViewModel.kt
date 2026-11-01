package com.fajar.cinescope.movie_detail.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.cinescope.core.common.DispatcherProvider
import com.fajar.cinescope.core.domain.model.MovieDetailData
import com.fajar.cinescope.core.domain.model.fold
import com.fajar.cinescope.core.domain.repository.MovieRepository
import com.fajar.cinescope.core.ui.mapper.DefaultErrorMapper
import com.fajar.cinescope.core.ui.model.UIText
import com.fajar.cinescope.movie_detail.presentation.mapper.DUIMapper
import com.fajar.cinescope.movie_detail.presentation.model.MovieDetailDUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
internal data class MovieDetailUIState(
    val isLoading: Boolean = false,
    val movieDetailDUI: MovieDetailDUI = MovieDetailDUI(),
    val errorUserMessage: UIText? = null,
    val isFavorite: Boolean = false
)

@HiltViewModel(assistedFactory = MovieDetailViewModel.Factory::class)
internal class MovieDetailViewModel @AssistedInject constructor(
    private val repository: MovieRepository,
    private val duiMapper: DUIMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val defaultErrorMapper: DefaultErrorMapper,
    @Assisted private val selectedMovieId: Long
) : ViewModel() {

    private var obtainedSelectedMovieDetail: MovieDetailData? = null
    private val _uiState = MutableStateFlow(MovieDetailUIState())
    val uiState = _uiState.asStateFlow()

    @Volatile
    private var hasBeenInitialized = false
    fun initializeIfNeeded() {
        if (hasBeenInitialized) return

        hasBeenInitialized = true
        loadMovieDetail()
        startFavoriteStatusFlow()
    }

    private var loadMovieDetailJob: Job? = null
    private fun loadMovieDetail() {
        if (loadMovieDetailJob?.isActive == true) return

        loadMovieDetailJob = viewModelScope.launch(dispatcherProvider.default) {
            _uiState.update { it.copy(isLoading = true) }

            repository.getMovieDetail(selectedMovieId)
                .fold(
                    onSuccess = { movieDetail ->
                        obtainedSelectedMovieDetail = movieDetail

                        val movieDetailDUI = duiMapper
                            .mapDetailToDUI(movieDetail)
                        _uiState.update {
                            it.copy(
                                movieDetailDUI = movieDetailDUI,
                                isLoading = false
                            )
                        }
                    },
                    onError = { error ->
                        val errorUserMessage = defaultErrorMapper
                            .getErrorStringResource(error)
                        _uiState.update {
                            it.copy(
                                errorUserMessage = errorUserMessage,
                                isLoading = false
                            )
                        }
                    }
                )

        }
    }

    fun toggleFavoriteStatus() {
        val selectedMovieDetail = obtainedSelectedMovieDetail ?: return

        viewModelScope.launch {
            val newFavoriteStatus = !_uiState.value.isFavorite
            if (newFavoriteStatus) {
                repository.addFavoriteMovie(movieDetailData = selectedMovieDetail)
            } else repository.deleteFromListFavorite(selectedMovieId)
        }
    }

    private fun startFavoriteStatusFlow() {
        viewModelScope.launch(dispatcherProvider.default) {
            repository.checkIsMovieInFavoriteList(movieId = selectedMovieId)
                .collectLatest { isFavorite -> _uiState.update { it.copy(isFavorite = isFavorite) } }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(selectedMovieId: Long): MovieDetailViewModel
    }
}