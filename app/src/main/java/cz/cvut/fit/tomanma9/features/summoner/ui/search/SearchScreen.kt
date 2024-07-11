package cz.cvut.fit.tomanma9.features.summoner.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.app.TajnakBusterThemeWithBackground
import cz.cvut.fit.tomanma9.app.ui.Screen
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.features.summoner.data.InMemorySummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.ui.common.GoBackIcon
import cz.cvut.fit.tomanma9.features.summoner.ui.common.SummonerCardColors
import cz.cvut.fit.tomanma9.features.summoner.ui.common.SummonerList
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = koinViewModel()) {
  val state by viewModel.summonerSearchState.collectAsState()

  FilterSummonerScreen(
    state = state,
    onGoBackClick = { navController.navigateUp() },
    onSearchClearClick = viewModel::clearSearch,
    onQueryChange = viewModel::searchSummoners,
    onSummonerClick = { navController.navigate(Screen.DetailScreen.route + "/${it.puuid}") },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSummonerScreen(
  state: SummonerSearchState,
  onGoBackClick: () -> Unit = {},
  onSearchClearClick: () -> Unit = {},
  onQueryChange: (String) -> Unit = {},
  onSummonerClick: (Summoner) -> Unit = {},
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          FilterSearchBar(
            query = state.query,
            onClearClick = onSearchClearClick,
            onQueryChange = onQueryChange,
          )
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
          ),
        navigationIcon = {
          GoBackIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer, onClick = onGoBackClick)
        },
      )
    }
  ) { paddingValues ->
    SummonerList(
      summoners = state.filteredSummoners,
      modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize(),
      summonerCardColors =
        SummonerCardColors(Color.Transparent, MaterialTheme.colorScheme.onBackground),
      onSummonerClick = onSummonerClick,
    )
  }
}

@Composable
fun FilterSearchBar(
  query: String,
  modifier: Modifier = Modifier,
  onClearClick: () -> Unit = {},
  onQueryChange: (String) -> Unit = {},
) {
  TextField(
    value = query,
    onValueChange = onQueryChange,
    placeholder = {
      Text(
        text = stringResource(R.string.filter_screen_search_bar_place_holder),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
      )
    },
    textStyle = MaterialTheme.typography.headlineSmall,
    modifier = modifier.fillMaxWidth(),
    colors =
      TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
      ),
    trailingIcon = {
      if (query.isNotEmpty()) {
        IconButton(onClick = onClearClick) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription =
              stringResource(R.string.filter_screen_clear_search_icon_description),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
          )
        }
      }
    },
  )
}

@Preview
@Composable
fun FilterSummonerScreenPreview() {
  val summoners = InMemorySummonerRepository().getAllTest()
  val query = "asd"

  TajnakBusterThemeWithBackground {
    FilterSummonerScreen(state = SummonerSearchState(summoners, query))
  }
}
