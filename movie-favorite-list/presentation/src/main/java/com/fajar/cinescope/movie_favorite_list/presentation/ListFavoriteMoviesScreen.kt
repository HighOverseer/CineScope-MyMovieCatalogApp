package com.fajar.cinescope.movie_favorite_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fajar.cinescope.core.domain.model.FavoriteMovieItemData
import com.fajar.cinescope.core.domain.model.Rating
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Grey90
import com.fajar.cinescope.movie_favorite_list.presentation.components.FavoriteMovieItemCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.fajar.cinescope.core.ui.R as CoreR

@Composable
internal fun ListFavoriteMoviesScreen(
    viewModel: ListFavoriteMoviesViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit = { },
    onNavigateUp: () -> Unit = { }
) {
    val favoriteMovies by viewModel.favoriteMovies
        .collectAsStateWithLifecycle()

    ListFavoriteMoviesScreen(
        modifier = Modifier,
        favoriteMovies = favoriteMovies,
        onNavigateUp = onNavigateUp,
        onNavigateToDetail = onNavigateToDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListFavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    favoriteMovies: ImmutableList<FavoriteMovieItemData> = persistentListOf(),
    onNavigateUp: () -> Unit = { },
    onNavigateToDetail: (Long) -> Unit = { }
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { focusManager.clearFocus() }
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    scrolledContainerColor = Color.White,
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.favorite_movies),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Black10
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    CompositionLocalProvider(
                        LocalRippleConfiguration provides RippleConfiguration(
                            color = Color.Black
                        )
                    ) {
                        IconButton(
                            onClick = onNavigateUp,
                        ) {
                            Icon(
                                imageVector = ImageVector
                                    .vectorResource(CoreR.drawable.ic_arrow_left),
                                contentDescription = stringResource(CoreR.string.navigate_back_to_previous_screen),
                                tint = Black10
                            )
                        }
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Grey90)
                .padding(innerPadding),

            ) {
            if (favoriteMovies.isNotEmpty()) {
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 32.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items = favoriteMovies, key = { it.id }) { item ->
                        FavoriteMovieItemCard(
                            item = item,
                            onClick = { onNavigateToDetail(item.id) },
                        )
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.weight(0.4f))
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        text = stringResource(R.string.no_movies_found_in_favorite_list),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.weight(0.6f))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListFavoriteMoviesScreenPreview() {
    CineScopeAppTheme {
        ListFavoriteMoviesScreen(
            favoriteMovies = remember {
                List(0) {
                    FavoriteMovieItemData(
                        id = it.toLong(),
                        title = "Blade Runner 2049",
                        imageUrl = "image.com",
                        rating = Rating(8.0, 10.0, 1000),
                        overview = "Lorem ipsum alskflsmflmskdf ksflks ksfldfl kskmf hjkjwejwjfnwjkn wkjenjkwnjkernjk  kwnejkrnwkjrnwkjn kjwnerkjnwjkrn werjk",
                    )
                }.toImmutableList()
            }
        )
    }
}