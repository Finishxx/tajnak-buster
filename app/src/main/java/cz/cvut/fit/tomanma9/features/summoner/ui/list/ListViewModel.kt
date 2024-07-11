package cz.cvut.fit.tomanma9.features.summoner.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.features.monitor.domain.NotifyOnNewGameUseCase
import cz.cvut.fit.tomanma9.features.summoner.data.SummonerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
  private val summonerRepository: SummonerRepository,
  private val notifyOnNewGameUseCase: NotifyOnNewGameUseCase,
) : ViewModel() {

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  private val _summonerListState: MutableStateFlow<SummonerListState> =
    MutableStateFlow(SummonerListState.Loading)
  val summonerListState: StateFlow<SummonerListState> = _summonerListState.asStateFlow()

  private val allSummonerFlow: StateFlow<List<Summoner>> =
    summonerRepository
      .getAllStream()
      .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf(),
      )

  init {
    viewModelScope.launch {
      _summonerListState.update { SummonerListState.Loaded(summonerRepository.getAll()) }

      allSummonerFlow.collect { summoners ->
        when (val state = _summonerListState.value) {
          is SummonerListState.Loading -> {}
          is SummonerListState.Loaded ->
            _summonerListState.update { state.copy(summoners = summoners) }
        }
      }
    }
  }

  fun doNotifications() {
    viewModelScope.launch { notifyOnNewGameUseCase() }
  }

  fun onHelpClick() {
    when (val state = _summonerListState.value) {
      is SummonerListState.Loaded -> {
        _summonerListState.update { state.copy(isHelpOpen = true) }
      }
      is SummonerListState.Loading -> {}
    }
  }

  fun onHelpConfirmClick() {
    when (val state = _summonerListState.value) {
      is SummonerListState.Loaded -> {
        _summonerListState.update { state.copy(isHelpOpen = false) }
      }
      is SummonerListState.Loading -> {}
    }
  }
}

sealed interface SummonerListState {

  data object Loading : SummonerListState

  data class Loaded(val summoners: List<Summoner>, val isHelpOpen: Boolean = false) :
    SummonerListState
}
