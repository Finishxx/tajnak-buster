package cz.cvut.fit.tomanma9.features.summoner.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.features.summoner.data.SummonerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val summonerRepository: SummonerRepository) : ViewModel() {

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  private val _summonerSearchState: MutableStateFlow<SummonerSearchState> =
    MutableStateFlow(SummonerSearchState(listOf(), ""))
  val summonerSearchState: StateFlow<SummonerSearchState> = _summonerSearchState.asStateFlow()

  private val queryFlow = MutableStateFlow("")

  private val allSummonersFlow: StateFlow<List<Summoner>> =
    summonerRepository
      .getAllStream()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf(),
      )

  init {
    viewModelScope.launch {
      combine(allSummonersFlow, queryFlow) { summoners, query ->
          SummonerSearchState(filteredSummoners = filterSummoners(query, summoners), query = query)
        }
        .collect { newState -> _summonerSearchState.value = newState }
    }
  }

  fun searchSummoners(query: String) {
    queryFlow.value = query
  }

  private fun filterSummoners(query: String, summoners: List<Summoner>): List<Summoner> =
    if (query.isEmpty()) listOf()
    else summoners.filter { it.name.contains(other = query, ignoreCase = true) }

  fun clearSearch() {
    _summonerSearchState.update { SummonerSearchState(filteredSummoners = listOf(), query = "") }
  }
}

data class SummonerSearchState(val filteredSummoners: List<Summoner>, val query: String)
