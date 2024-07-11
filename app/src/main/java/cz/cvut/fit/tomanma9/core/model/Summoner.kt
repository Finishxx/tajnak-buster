package cz.cvut.fit.tomanma9.core.model

data class Summoner(
  val name: String,
  val tag: String,
  val puuid: String,
  val iconId: Int,
  val game: Game?,
)
