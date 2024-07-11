package cz.cvut.fit.tomanma9.core.model

sealed interface SummonerStatus {
  data class InGame(val game: Game) : SummonerStatus

  data object NotInGame : SummonerStatus
}
