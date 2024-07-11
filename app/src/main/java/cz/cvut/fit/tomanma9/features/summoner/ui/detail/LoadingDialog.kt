package cz.cvut.fit.tomanma9.features.summoner.ui.detail

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoadingDialog(message: String) {
  AlertDialog(
    onDismissRequest = {},
    confirmButton = {},
    icon = { CircularProgressIndicator(color = MaterialTheme.colorScheme.onTertiaryContainer) },
    text = {
      Text(
        textAlign = TextAlign.Center,
        text = message,
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
  )
}
