package cz.cvut.fit.tomanma9.core.domain

import cz.cvut.fit.tomanma9.core.data.riotApi.api.SpectatorApi
import cz.cvut.fit.tomanma9.core.model.Game
import cz.cvut.fit.tomanma9.core.model.Summoner

class CheckGameStatusUseCase(private val spectatorApi: SpectatorApi) {

  suspend operator fun invoke(summoner: Summoner): Game? = spectatorApi.getGame(summoner.puuid)
}
