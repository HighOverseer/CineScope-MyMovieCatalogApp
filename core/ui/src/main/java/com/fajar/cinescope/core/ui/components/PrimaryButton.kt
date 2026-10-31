package com.fajar.cinescope.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Green55
import com.fajar.cinescope.core.ui.theme.Grey70

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier, text: String = "",
    onClick: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(vertical = 14.dp),
    enabled: Boolean = true,
    containerColor: Color = Green55
) {

    ElevatedButton(
        modifier = modifier,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor,
            disabledContentColor = Grey70,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    CineScopeAppTheme {
        PrimaryButton()
    }
}