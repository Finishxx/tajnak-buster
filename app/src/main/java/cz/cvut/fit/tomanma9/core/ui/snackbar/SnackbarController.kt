package cz.cvut.fit.tomanma9.core.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface SnackbarController {
  fun showMessage(
    message: String,
    actionLabel: String?,
    withDismissAction: Boolean,
    duration: SnackbarDuration,
    onSnackbarResult: (SnackbarResult) -> Unit,
  )
}

// Inspiration from here:
// https://afigaliyev.medium.com/snackbar-state-management-best-practices-for-jetpack-compose-1a5963d86d98
@Immutable
class SnackbarControllerImpl(
  private val snackbarHostState: SnackbarHostState,
  private val coroutineScope: CoroutineScope,
) : SnackbarController {
  override fun showMessage(
    message: String,
    actionLabel: String?,
    withDismissAction: Boolean,
    duration: SnackbarDuration,
    onSnackbarResult: (SnackbarResult) -> Unit,
  ) {
    coroutineScope.launch {
      snackbarHostState
        .showSnackbar(
          message = message,
          actionLabel = actionLabel,
          withDismissAction = withDismissAction,
          duration = duration,
        )
        .let(onSnackbarResult)
    }
  }
}

class SnackbarControllerMock : SnackbarController {
  override fun showMessage(
    message: String,
    actionLabel: String?,
    withDismissAction: Boolean,
    duration: SnackbarDuration,
    onSnackbarResult: (SnackbarResult) -> Unit,
  ) {}
}

@Composable
fun ProvideSnackbarController(
  snackbarHostState: SnackbarHostState,
  coroutineScope: CoroutineScope,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
    value =
      LocalSnackbarController provides
        SnackbarControllerImpl(
          snackbarHostState = snackbarHostState,
          coroutineScope = coroutineScope,
        ),
    content = content,
  )
}

val LocalSnackbarController =
  staticCompositionLocalOf<SnackbarController> { error("No SnackbarController provided") }

@Composable
fun ProvideSnackbarControllerMock(content: @Composable () -> Unit) {
  CompositionLocalProvider(
    value = LocalSnackbarController provides SnackbarControllerMock(),
    content = content,
  )
}
