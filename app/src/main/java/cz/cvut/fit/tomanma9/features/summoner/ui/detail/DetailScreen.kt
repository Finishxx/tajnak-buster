package cz.cvut.fit.tomanma9.features.summoner.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.app.TajnakBusterThemeWithBackground
import cz.cvut.fit.tomanma9.core.model.Game
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.core.model.SummonerStatus
import cz.cvut.fit.tomanma9.features.summoner.data.InMemorySummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.ui.common.GoBackIcon
import cz.cvut.fit.tomanma9.features.summoner.ui.common.LoadingScreen
import cz.cvut.fit.tomanma9.features.summoner.ui.common.SummonerIcon
import cz.cvut.fit.tomanma9.features.summoner.ui.common.SummonerStatusIndicator
import org.koin.androidx.compose.koinViewModel

// Screen displaying history of Summoner and all info about him

@Composable
fun DetailScreen(navController: NavController, viewModel: DetailViewModel = koinViewModel()) {
  val state = viewModel.summonerDetailState.collectAsState()

  when (val stateValue = state.value) {
    is SummonerDetailState.Loading -> LoadingDetailScreen()
    is SummonerDetailState.LoadedUnsuccessfully ->
      InfoDialogue(
        message = stringResource(R.string.detail_screen_unsuccessful_loading_info),
        buttonText = stringResource(R.string.back_button),
        onConfirm = { navController.navigateUp() },
      )
    is SummonerDetailState.Loaded ->
      DetailScreen(
        state = stateValue,
        onDeleteSummoner = { viewModel.deleteSummoner(stateValue.summoner) },
        onBackButtonClick = { navController.navigateUp() },
        onRefreshClick = { viewModel.refreshSummoner(stateValue.summoner) },
      )
    is SummonerDetailState.Deleting -> DeletingDetailScreen(state = stateValue)
    is SummonerDetailState.Deleted -> {
      InfoDialogue(
        message = stringResource(R.string.detail_screen_successful_delete),
        buttonText = stringResource(R.string.back_button),
        onConfirm = { navController.navigateUp() },
      )
    }
  }
}

@Composable
fun LoadingDetailScreen() {
  LoadingScreen()
}

@Composable
fun InfoDialogue(message: String, buttonText: String, onConfirm: () -> Unit) {
  AlertDialog(
    onDismissRequest = {},
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    title = {
      Text(
        text = message,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
    confirmButton = {
      Button(
        onClick = onConfirm,
        colors =
          ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
      ) {
        Text(
          text = buttonText,
          color = MaterialTheme.colorScheme.onTertiaryContainer,
          style = MaterialTheme.typography.headlineSmall,
        )
      }
    },
  )
}

@Composable
fun DeletingDetailScreen(state: SummonerDetailState.Deleting) {
  LoadingDialog(message = stringResource(R.string.detail_screen_when_deleting_text))

  DetailScreen(summoner = state.summoner)
}

@Composable
fun DetailScreen(
  state: SummonerDetailState.Loaded,
  onDeleteSummoner: () -> Unit = {},
  onBackButtonClick: () -> Unit = {},
  onRefreshClick: () -> Unit = {},
) {
  DetailScreen(
    summoner = state.summoner,
    isRefreshAvailable = state.isRefreshAvailable,
    onDeleteSummoner = onDeleteSummoner,
    onBackButtonClick = onBackButtonClick,
    onRefreshClick = onRefreshClick,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
  summoner: Summoner,
  isRefreshAvailable: Boolean = false,
  onDeleteSummoner: () -> Unit = {},
  onBackButtonClick: () -> Unit = {},
  onRefreshClick: () -> Unit = {},
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = summoner.name,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(Modifier.width(8.dp))
            SummonerStatusIndicator(isActive = summoner.game != null)
          }
        },
        navigationIcon = {
          GoBackIcon(
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = onBackButtonClick,
          )
        },
        actions = {
          IconButton(onClick = onDeleteSummoner) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription =
                stringResource(R.string.detail_screen_delete_summoner_icon_description),
              tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
          }
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
          ),
      )
    }
  ) {
    Card(
      modifier = Modifier.padding(it).padding(10.dp).fillMaxWidth().fillMaxWidth(),
      colors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
      elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
      LazyColumn {
        val padding = Modifier.padding(16.dp)

        item {
          SummonerProfile(
            name = summoner.name,
            tag = summoner.tag,
            iconId = summoner.iconId,
            modifier = padding,
          )
        }
        item { Divider(color = MaterialTheme.colorScheme.outlineVariant) }
        item {
          fun Game?.toSummonerStatus(): SummonerStatus =
            when (this) {
              null -> SummonerStatus.NotInGame
              else -> SummonerStatus.InGame(this)
            }

          SummonerStatus(
            status = summoner.game.toSummonerStatus(),
            isRefreshAvailable = isRefreshAvailable,
            modifier = padding,
            onRefreshClick = onRefreshClick,
          )
        }
        item { Spacer(modifier = padding.height(24.dp)) }
      }
    }
  }
}

@Composable
fun SummonerStatus(
  status: SummonerStatus,
  isRefreshAvailable: Boolean,
  modifier: Modifier = Modifier,
  onRefreshClick: () -> Unit = {},
) {

  @Composable
  fun statusToText(status: SummonerStatus): String =
    when (status) {
      is SummonerStatus.InGame -> stringResource(R.string.detail_screen_in_game_text)
      is SummonerStatus.NotInGame -> stringResource(R.string.detail_screen_not_in_game_text)
    }

  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
  ) {
    Column(modifier = modifier) {
      Text(
        text = stringResource(R.string.detail_screen_status_label),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.bodyLarge,
      )
      Text(
        text = statusToText(status),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.headlineSmall,
      )
    }
    if (isRefreshAvailable) {
      IconButton(onClick = onRefreshClick) {
        Icon(
          Icons.Default.Refresh,
          contentDescription = stringResource(R.string.detail_screen_refresh_button_description),
          tint = MaterialTheme.colorScheme.onSecondaryContainer,
          modifier = Modifier.fillMaxSize(),
        )
      }
    } else {
      CircularProgressIndicator()
    }
  }
}

@Composable
fun SummonerProfile(name: String, tag: String, iconId: Int, modifier: Modifier = Modifier) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    SummonerIcon(
      iconId = iconId,
      description = stringResource(R.string.summoner_icon),
      modifier = Modifier.size(140.dp).clip(RoundedCornerShape(22.dp)),
    )
    Spacer(modifier = Modifier.width(16.dp))
    Column() {
      Text(
        text = name,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.headlineMedium,
      )
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "#$tag",
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.headlineSmall,
      )
    }
  }
}

@Preview
@Composable
fun LoadingPreview() {
  TajnakBusterThemeWithBackground { LoadingDetailScreen() }
}

@Preview
@Composable
fun LoadedPreview() {
  val repository = InMemorySummonerRepository()
  TajnakBusterThemeWithBackground {
    DetailScreen(SummonerDetailState.Loaded(repository.getAllTest().first(), true))
  }
}

@Preview
@Composable
fun DeletingPreview() {
  val repository = InMemorySummonerRepository()
  TajnakBusterThemeWithBackground {
    DeletingDetailScreen(SummonerDetailState.Deleting(repository.getAllTest().first()))
  }
}
