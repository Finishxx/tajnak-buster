package cz.cvut.fit.tomanma9.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cz.cvut.fit.tomanma9.app.ui.MainScreen
import cz.cvut.fit.tomanma9.core.ui.snackbar.ProvideSnackbarController
import cz.cvut.fit.tomanma9.core.ui.theme.TajnakBusterTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TajnakBusterThemeWithBackground {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope: CoroutineScope = rememberCoroutineScope()

        ProvideSnackbarController(
          snackbarHostState = snackbarHostState,
          coroutineScope = coroutineScope,
        ) {
          Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
            MainScreen(modifier = Modifier.padding(innerPadding))
          }
        }
      }
    }
  }
}

@Composable
fun TajnakBusterThemeWithBackground(content: @Composable () -> Unit) {
  TajnakBusterTheme {
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background,
      content = content,
    )
  }
}
