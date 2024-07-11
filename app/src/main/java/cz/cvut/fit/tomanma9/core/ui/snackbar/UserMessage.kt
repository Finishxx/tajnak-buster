package cz.cvut.fit.tomanma9.core.ui.snackbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UserMessage {

  data class Text(val text: String) : UserMessage

  class StringResource(@StringRes val resId: Int, vararg val formatArgs: Any) : UserMessage

  companion object {
    fun from(text: String) = Text(text)

    fun from(@StringRes resId: Int, vararg formatArgs: Any) = StringResource(resId, formatArgs)
  }
}

@Composable
fun UserMessage.asString() =
  when (this) {
    is UserMessage.Text -> text
    is UserMessage.StringResource -> stringResource(id = resId, formatArgs = formatArgs)
  }
