package cz.cvut.fit.tomanma9.features.summoner.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.cvut.fit.tomanma9.R

@Composable
fun GoBackIcon(tint: Color, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(
      imageVector = Icons.Default.ArrowBack,
      contentDescription = stringResource(R.string.back_button),
      modifier = Modifier.fillMaxSize(),
      tint = tint,
    )
  }
}
