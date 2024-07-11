package cz.cvut.fit.tomanma9.core.data.riotApi.response

import kotlinx.serialization.Serializable

@Serializable
data class SpectatorResponse(
  val gameId: Long,
  val gameType: String,
  val gameMode: String,
  val gameStartTime: Long,
)
