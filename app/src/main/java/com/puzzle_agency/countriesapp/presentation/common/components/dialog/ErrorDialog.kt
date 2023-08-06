package com.puzzle_agency.countriesapp.presentation.common.components.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.puzzle_agency.countriesapp.R
import com.puzzle_agency.countriesapp.ui.theme.ColorScheme
import com.puzzle_agency.countriesapp.ui.theme.Dimens
import com.puzzle_agency.countriesapp.ui.theme.Typography
import com.puzzle_agency.countriesapp.ui.theme.a2SemiBoldTextStyle
import com.puzzle_agency.countriesapp.ui.theme.b0BoldTextStyle
import com.puzzle_agency.countriesapp.ui.theme.b0RegularTextStyle

@Composable
fun SimpleErrorDialog(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.error),
                style = Typography.a2SemiBoldTextStyle,
                color = Color.White
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.error_appeared),
                style = Typography.b0RegularTextStyle,
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.padding(
                    bottom = Dimens.spacing_normal,
                    end = Dimens.spacing_normal
                )
            ) {
                Text(
                    text = stringResource(id = R.string.ok).uppercase(),
                    style = Typography.b0BoldTextStyle,
                    color = ColorScheme.primary
                )
            }
        },
    )
}