package cz.cvut.fit.tomanma9.core.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult

sealed interface SnackbarMessage {

  data class Text(
    val userMessage: UserMessage,
    val actionLabelMessage: UserMessage? = null,
    val withDismissAction: Boolean = false,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val onSnackbarResult: (SnackbarResult) -> Unit = {},
  ) : SnackbarMessage

  companion object {
    fun from(
      userMessage: UserMessage,
      actionLabelMessage: UserMessage? = null,
      withDismissAction: Boolean = false,
      duration: SnackbarDuration = SnackbarDuration.Short,
      onSnackbarResult: (SnackbarResult) -> Unit = {},
    ): SnackbarMessage =
      Text(
        userMessage = userMessage,
        actionLabelMessage = actionLabelMessage,
        withDismissAction = withDismissAction,
        duration = duration,
        onSnackbarResult = onSnackbarResult,
      )
  }
}
