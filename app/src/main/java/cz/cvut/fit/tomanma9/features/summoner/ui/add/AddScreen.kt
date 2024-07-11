package cz.cvut.fit.tomanma9.features.summoner.ui.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.app.TajnakBusterThemeWithBackground
import cz.cvut.fit.tomanma9.core.ui.snackbar.ProvideSnackbarControllerMock
import cz.cvut.fit.tomanma9.core.ui.snackbar.SnackbarMessage
import cz.cvut.fit.tomanma9.core.ui.snackbar.SnackbarMessageHandler
import cz.cvut.fit.tomanma9.features.summoner.data.InMemorySummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.ui.common.GoBackIcon
import org.koin.androidx.compose.koinViewModel

// Screen for adding a new summoner with goBack button

@Composable
fun AddScreen(navController: NavController, viewModel: AddViewModel = koinViewModel()) {
  val state = viewModel.summonerAddState.collectAsState()

  when (val stateValue = state.value) {
    is SummonerAddState.Adding -> {
      AddScreen(
        name = stateValue.name,
        tag = stateValue.tag,
        snackbarMessage = stateValue.snackbarMessage,
        onSnackbarDismiss = viewModel::onSnackbarDismiss,
        onNameChange = viewModel::onNameChange,
        onTagChange = viewModel::onTagChange,
        onGoBackClick = { navController.navigateUp() },
        onSubmit = viewModel::onSubmit,
      )
    }
    is SummonerAddState.Confirming -> {
      ConfirmingAddScreen(stateValue.name, stateValue.tag, stateValue.snackbarMessage)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmingAddScreen(name: String, tag: String, snackbarMessage: SnackbarMessage?) {
  AlertDialog(
    onDismissRequest = {},
    confirmButton = {},
    properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    text = {
      Text(
        textAlign = TextAlign.Center,
        text = stringResource(R.string.add_screen_adding_text),
        color = MaterialTheme.colorScheme.onTertiaryContainer,
        style = MaterialTheme.typography.headlineMedium,
      )
    },
    icon = { CircularProgressIndicator(color = MaterialTheme.colorScheme.onTertiaryContainer) },
  )

  AddScreen(name = name, tag = tag, snackbarMessage = snackbarMessage)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
  name: String,
  tag: String,
  snackbarMessage: SnackbarMessage?,
  onSnackbarDismiss: () -> Unit = {},
  onNameChange: (String) -> Unit = {},
  onTagChange: (String) -> Unit = {},
  onGoBackClick: () -> Unit = {},
  onSubmit: () -> Unit = {},
) {
  SnackbarMessageHandler(snackbarMessage, onSnackbarDismiss)

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Add Summoner",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.headlineMedium,
          )
        },
        navigationIcon = {
          GoBackIcon(tint = MaterialTheme.colorScheme.onPrimaryContainer, onClick = onGoBackClick)
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
          ),
      )
    }
  ) {
    Card(
      modifier = Modifier.padding(it).padding(10.dp).fillMaxWidth(),
      colors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
      elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
      LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
      ) {
        item {
          InputField(
            label = stringResource(R.string.add_screen_summoner_name_field_label),
            value = name,
            onValueChange = onNameChange,
          )
        }
        item {
          InputField(
            label = stringResource(R.string.add_screen_summoner_tag_field_label),
            value = tag,
            onValueChange = onTagChange,
          )
        }
        item {
          Button(
            onClick = onSubmit,
            colors =
              ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
              ),
          ) {
            Text(
              "Submit",
              color = MaterialTheme.colorScheme.onSecondaryContainer,
              style = MaterialTheme.typography.headlineSmall,
            )
          }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
      }
    }
  }
}

@Composable
fun InputField(
  label: String,
  value: String,
  modifier: Modifier = Modifier,
  onValueChange: (String) -> Unit = {},
) {
  Column(modifier = modifier) {
    Text(
      text = label,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
      style = MaterialTheme.typography.headlineMedium,
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = value,
      onValueChange = onValueChange,
      colors =
        TextFieldDefaults.colors(
          focusedIndicatorColor = MaterialTheme.colorScheme.outline,
          unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface,
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          focusedTextColor = MaterialTheme.colorScheme.onSurface,
          unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
      textStyle = MaterialTheme.typography.headlineSmall,
    )
  }
}

@Preview
@Composable
fun AddScreenPreview() {
  val summoners = InMemorySummonerRepository().getAllTest()
  val summoner = summoners.first()

  ProvideSnackbarControllerMock {
    TajnakBusterThemeWithBackground { AddScreen(name = summoner.name, tag = summoner.tag, null) }
  }
}

@Preview
@Composable
fun ConfirmingAddScreenPreview() {
  val summoners = InMemorySummonerRepository().getAllTest()
  val summoner = summoners.first()

  TajnakBusterThemeWithBackground {
    ProvideSnackbarControllerMock {
      ConfirmingAddScreen(name = summoner.name, tag = summoner.tag, null)
    }
  }
}
