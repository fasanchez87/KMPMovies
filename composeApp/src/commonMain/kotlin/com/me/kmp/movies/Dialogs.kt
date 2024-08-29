package com.me.kmp.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ErrorDialog(
    title: String = "Information",
    description: String,
    textButtonOk: String = "Accept",
    onClickOkAction: () -> Unit = {}
) {
    val show = rememberSaveable { mutableStateOf(true) }

    if (show.value) {

        Dialog(
            onDismissRequest = {
                show.value = false
            },
            properties =
            DialogProperties(
                dismissOnBackPress = show.value,
                dismissOnClickOutside = show.value,
            ),
        ) {
            Card(
                modifier =
                Modifier
                    .testTag("dialog")
                    .fillMaxWidth(),
                colors =
                CardDefaults
                    .cardColors(
                        containerColor = Color.White,
                    ),
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    Row(
                        modifier =
                        Modifier
                            .align(Alignment.End),
                    ) {
                        TextButton(
                            onClick = { show.value = false },
                            enabled = true,
                        ) {
                            Text(
                                text = textButtonOk,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }
            }
        }
    }
}
