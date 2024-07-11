package cz.cvut.fit.tomanma9.features.summoner.ui.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.core.domain.CheckGameStatusUseCase
import cz.cvut.fit.tomanma9.core.ui.snackbar.SnackbarMessage
import cz.cvut.fit.tomanma9.core.ui.snackbar.UserMessage
import cz.cvut.fit.tomanma9.features.summoner.data.SummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.domain.VerifySummonerUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddViewModel(
  private val summonerRepository: SummonerRepository,
  private val verifySummonerUseCase: VerifySummonerUseCase,
  private val checkGameStatusUseCase: CheckGameStatusUseCase,
) : ViewModel(), KoinComponent {

  private val context: Context by inject()

  private val _summonerAddState: MutableStateFlow<SummonerAddState> =
    MutableStateFlow(SummonerAddState.Adding("", "", null))
  val summonerAddState: StateFlow<SummonerAddState> = _summonerAddState.asStateFlow()

  fun onNameChange(name: String) {
    when (val value = summonerAddState.value) {
      is SummonerAddState.Adding -> {
        _summonerAddState.value = value.copy(name = name)
      }
      else -> {
        throw IllegalStateException("onNameChange called when not in Adding state!")
      }
    }
  }

  fun onTagChange(tag: String) {
    when (val state = summonerAddState.value) {
      is SummonerAddState.Adding -> {
        _summonerAddState.update { state.copy(tag = tag) }
      }
      else -> {
        throw IllegalStateException("onTagChange called when not in Adding state!!")
      }
    }
  }

  fun onSnackbarDismiss() {
    when (val state = _summonerAddState.value) {
      is SummonerAddState.Adding -> {
        _summonerAddState.update { state.copy(snackbarMessage = null) }
      }
      is SummonerAddState.Confirming -> {
        _summonerAddState.update { state.copy(snackbarMessage = null) }
      }
    }
  }

  fun onSubmit() {
    when (val state = _summonerAddState.value) {
      is SummonerAddState.Adding -> {
        _summonerAddState.value = SummonerAddState.Confirming(state.name, state.tag, null)
        viewModelScope.launch {
          delay(timeMillis = 1000L)

          if (summonerRepository.getByRiotId(state.name, state.tag) != null) {
            _summonerAddState.update {
              state.copy(
                snackbarMessage =
                  SnackbarMessage.from(
                    userMessage =
                      UserMessage.from(
                        text =
                          context.getString(R.string.add_screen_snackbar_summoner_already_present)
                      )
                  )
              )
            }
            return@launch
          }

          val summoner = verifySummonerUseCase.invoke(state.name, state.tag)

          if (summoner == null) {
            _summonerAddState.update {
              state.copy(
                snackbarMessage =
                  SnackbarMessage.from(
                    userMessage =
                      UserMessage.from(
                        text = context.getString(R.string.add_screen_snackbar_summoner_not_found)
                      )
                  )
              )
            }
          } else {
            summonerRepository.insert(summoner.copy(game = checkGameStatusUseCase.invoke(summoner)))

            _summonerAddState.value =
              SummonerAddState.Adding(
                name = "",
                tag = "",
                snackbarMessage =
                  SnackbarMessage.from(
                    userMessage =
                      UserMessage.from(
                        text =
                          context.getString(
                            R.string.add_screen_snackbar_summoner_added_successfully
                          )
                      )
                  ),
              )
          }
        }
      }
      else -> {
        throw IllegalStateException("onSubmit called when not in Adding state!")
      }
    }
  }
}

sealed interface SummonerAddState {
  data class Adding(val name: String, val tag: String, val snackbarMessage: SnackbarMessage?) :
    SummonerAddState

  data class Confirming(val name: String, val tag: String, val snackbarMessage: SnackbarMessage?) :
    SummonerAddState
}
