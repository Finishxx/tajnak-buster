package cz.cvut.fit.tomanma9.core.data.riotApi.api

import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiDescription
import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiKeyProvider
import cz.cvut.fit.tomanma9.core.data.riotApi.getCurrentGameUrl
import cz.cvut.fit.tomanma9.core.model.Game

class SpectatorApi(
  private val riotApiDescription: RiotApiDescription,
  private val riotApiKeyProvider: RiotApiKeyProvider,
) {

  suspend fun getGame(puuid: String): Game? {
    val gameResponse =
      riotApiDescription
        .getCurrentGame(getCurrentGameUrl(puuid), riotApiKeyProvider.provide())
        .body() ?: return null

    return Game(gameResponse.gameId)
  }
}
