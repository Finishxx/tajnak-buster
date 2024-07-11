package cz.cvut.fit.tomanma9.features.summoner.ui.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.app.TajnakBusterThemeWithBackground
import cz.cvut.fit.tomanma9.app.ui.Screen
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.core.notification.ui.Permission
import cz.cvut.fit.tomanma9.features.summoner.data.InMemorySummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.ui.common.LoadingScreen
import cz.cvut.fit.tomanma9.features.summoner.ui.common.SummonerList
import org.koin.androidx.compose.koinViewModel

@Composable
fun SummonerListScreen(navController: NavController, viewModel: ListViewModel = koinViewModel()) {
  val state by viewModel.summonerListState.collectAsState()

  Permission(rationale = stringResource(R.string.list_screen_permission_rationale))

  SummonerListScreen(
    state = state,
    onSearchClick = { navController.navigate(Screen.SearchScreen.route) },
    onSummonerClick = { navController.navigate(Screen.DetailScreen.route + "/${it.puuid}") },
    onAddSummonerClick = { navController.navigate(Screen.AddScreen.route) },
    doNotifications = viewModel::doNotifications,
    onHelpClick = viewModel::onHelpClick,
    onHelpConfirmClick = viewModel::onHelpConfirmClick,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummonerListScreen(
  state: SummonerListState,
  onSearchClick: () -> Unit = {},
  onSummonerClick: (Summoner) -> Unit = {},
  onAddSummonerClick: () -> Unit = {},
  doNotifications: () -> Unit = {},
  onHelpClick: () -> Unit = {},
  onHelpConfirmClick: () -> Unit = {},
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(R.string.list_screen_monitored_summoners_headline),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
          )
        },
        actions = {
          IconButton(onClick = onHelpClick) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = stringResource(R.string.info_button_description),
              tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
          }
          IconButton(onClick = doNotifications) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = stringResource(R.string.list_screen_do_notification_description),
              tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
          }
          IconButton(onClick = onSearchClick) {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = stringResource(R.string.list_screen_search_description),
              tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
          }
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
          ),
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = onAddSummonerClick) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = stringResource(R.string.add_summoner_button),
        )
      }
    },
  ) { paddingValues ->
    when (state) {
      is SummonerListState.Loading -> {
        LoadingScreen()
      }
      is SummonerListState.Loaded -> {
        if (state.isHelpOpen) {
          AlertDialog(
            onDismissRequest = onHelpConfirmClick,
            confirmButton = {
              IconButton(onClick = onHelpConfirmClick) {
                Icon(
                  imageVector = Icons.Default.Done,
                  contentDescription = stringResource(R.string.help_confirm_button),
                )
              }
            },
            text = {
              Text(
                text = stringResource(R.string.riot_legal_boilerplate),
                style = MaterialTheme.typography.bodyMedium,
              )
            },
          )
        }
        SummonerList(
          summoners = state.summoners,
          modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize(),
          onSummonerClick = onSummonerClick,
        )
      }
    }
  }
}

@Preview
@Composable
fun LoadedSummonerScreenPreview() {
  val summoners = InMemorySummonerRepository().getAllTest()

  TajnakBusterThemeWithBackground {
    SummonerListScreen(state = SummonerListState.Loaded(summoners))
  }
}

@Preview
@Composable
fun LoadingSummonerScreenPreview() {
  TajnakBusterThemeWithBackground { SummonerListScreen(state = SummonerListState.Loading) }
}
