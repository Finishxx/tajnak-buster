package cz.cvut.fit.tomanma9.core.notification.ui

import android.Manifest
import android.os.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import cz.cvut.fit.tomanma9.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
  rationale: String,
  permissionNotAvailableContent: @Composable () -> Unit = {},
  content: @Composable () -> Unit = {},
) {
  val permissions: MutableList<String> = mutableListOf()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    permissions += Manifest.permission.POST_NOTIFICATIONS
  }
  val permissionState = rememberMultiplePermissionsState(permissions)

  if (permissionState.allPermissionsGranted) {
    content()
  } else {
    if (permissionState.shouldShowRationale) {
      AlertDialog(
        onDismissRequest = {},
        title = {
          Text(
            text = stringResource(R.string.notification_permission_title),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.headlineMedium,
          )
        },
        text = {
          Text(
            text = rationale,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.headlineSmall,
          )
        },
        confirmButton = {
          Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
            Text(text = "OK")
          }
        },
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
      )
    } else {
      // Either user got a permission request for the first time or declined two times or more
      LaunchedEffect(Unit) { permissionState.launchMultiplePermissionRequest() }
      permissionNotAvailableContent()
    }
  }
}
