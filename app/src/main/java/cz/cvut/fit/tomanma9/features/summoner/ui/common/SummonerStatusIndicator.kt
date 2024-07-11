package cz.cvut.fit.tomanma9.features.summoner.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fit.tomanma9.R

@Composable
fun SummonerStatusIndicator(isActive: Boolean) {
  if (isActive) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = stringResource(R.string.summoner_status_indicator_active),
      tint = MaterialTheme.colorScheme.primary,
    )
  } else {
    Icon(
      imageVector = Icons.Default.Close,
      contentDescription = stringResource(R.string.summoner_status_indicator_inactive),
      tint = MaterialTheme.colorScheme.error,
    )
  }
}
