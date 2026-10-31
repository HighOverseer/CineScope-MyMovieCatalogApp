package com.fajar.cinescope.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fajar.cinescope.core.ui.R
import com.fajar.cinescope.core.ui.theme.Black10
import com.fajar.cinescope.core.ui.theme.CineScopeAppTheme
import com.fajar.cinescope.core.ui.theme.Green60
import com.fajar.cinescope.core.ui.theme.Grey90
import com.fajar.cinescope.core.ui.theme.Orange80

@Composable
fun GeneralDialog(
    modifier: Modifier = Modifier,
    title: String = "",
    message: String = "",
    onClick: () -> Unit = { },
    onDismissRequest: () -> Unit = { },
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier
                    .widthIn(max = 420.dp)
                    .wrapContentSize()
                    .background(color = Grey90, shape = RoundedCornerShape(2.dp))
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography
                        .labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                    fontSize = 18.sp,
                    color = Orange80
                )

                Spacer(Modifier.height(12.dp))

                HorizontalDivider(
                    color = Green60
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography
                        .bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.02.sp
                        ),
                    fontSize = 14.sp,
                    color = Black10
                )

                Spacer(Modifier.height(28.dp))

                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.okay),
                    onClick = onClick,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun GeneralDialogPreview() {
    CineScopeAppTheme {
        Column(Modifier.fillMaxSize()) {
            GeneralDialog(
                title = "Error",
                message = "Something went wrong, please try again..",
                onClick = { },
                onDismissRequest = { }
            )
        }
    }
}