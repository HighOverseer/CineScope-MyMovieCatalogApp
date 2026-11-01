package com.fajar.cinescope.movie_previews.presentation.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.ui.components.PrimaryButton
import com.fajar.cinescope.movie_previews.presentation.R

@Composable
internal fun RetryLoadingSection(
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onRetry: () -> Unit = { },
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        PrimaryButton(
            modifier = Modifier
                .widthIn(min = 120.dp),
            text = stringResource(R.string.retry),
            onClick = onRetry,
            contentPadding = PaddingValues(vertical = 0.dp)
        )
    }
}