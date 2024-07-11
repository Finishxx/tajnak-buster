package cz.cvut.fit.tomanma9.features.summoner.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.tomanma9.app.ui.Screen
import cz.cvut.fit.tomanma9.core.domain.CheckGameStatusUseCase
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.features.summoner.data.SummonerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
  private val summonerRepository: SummonerRepository,
  private val savedStateHandle: SavedStateHandle,
  private val checkGameStatusUseCase: CheckGameStatusUseCase,
) : ViewModel() {

  private val summonerPuuid: String
    get() = savedStateHandle[Screen.DetailScreen.ID_KEY] ?: ""

  private val _summonerDetailState: MutableStateFlow<SummonerDetailState> =
    MutableStateFlow(SummonerDetailState.Loading)
  val summonerDetailState: StateFlow<SummonerDetailState> = _summonerDetailState.asStateFlow()

  init {
    viewModelScope.launch {
      val summoner = summonerRepository.getByPuuid(summonerPuuid)
      if (summoner == null) {
        _summonerDetailState.update { SummonerDetailState.LoadedUnsuccessfully }
      } else {
        _summonerDetailState.update { SummonerDetailState.Loaded(summoner, true) }
      }
    }
  }

  fun deleteSummoner(summoner: Summoner) {
    _summonerDetailState.update { SummonerDetailState.Deleting(summoner) }
    viewModelScope.launch {
      summonerRepository.removeByPuuid(summonerPuuid)
      delay(timeMillis = 1000L)
      _summonerDetailState.update { SummonerDetailState.Deleted }
    }
  }

  fun refreshSummoner(summoner: Summoner) {
    viewModelScope.launch {
      val game = checkGameStatusUseCase.invoke(summoner)

      val newSummoner = summoner.copy(game = game)

      summonerRepository.update(newSummoner)

      when (val state = summonerDetailState.value) {
        is SummonerDetailState.Loaded ->
          _summonerDetailState.update { state.copy(summoner = newSummoner) }
        is SummonerDetailState.Deleting ->
          _summonerDetailState.update { state.copy(summoner = newSummoner) }
        else -> {}
      }
    }
  }
}

sealed interface SummonerDetailState {
  data object Loading : SummonerDetailState

  data object LoadedUnsuccessfully : SummonerDetailState

  data class Loaded(val summoner: Summoner, val isRefreshAvailable: Boolean) : SummonerDetailState

  data class Deleting(val summoner: Summoner) : SummonerDetailState

  data object Deleted : SummonerDetailState
}
