package cz.cvut.fit.tomanma9.features.summoner.domain

import cz.cvut.fit.tomanma9.core.data.riotApi.api.AccountApi
import cz.cvut.fit.tomanma9.core.data.riotApi.api.SummonerApi
import cz.cvut.fit.tomanma9.core.model.Summoner

class VerifySummonerUseCase(
  private val summonerApi: SummonerApi,
  private val accountApi: AccountApi,
) {

  suspend fun invoke(summonerName: String, summonerTag: String): Summoner? {
    val puuid = accountApi.puuidByNameTag(summonerName, summonerTag) ?: return null

    val iconId = summonerApi.getIconId(puuid) ?: return null

    return Summoner(summonerName, summonerTag, puuid, iconId, null)
  }
}
