package cz.cvut.fit.tomanma9.core.data.riotApi.api

import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiDescription
import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiKeyProvider
import cz.cvut.fit.tomanma9.core.data.riotApi.getSummonerUrl

class SummonerApi(
  private val riotApiDescription: RiotApiDescription,
  private val riotApiKeyProvider: RiotApiKeyProvider,
) {

  suspend fun getIconId(puuid: String): Int? =
    riotApiDescription
      .getSummoner(getSummonerUrl(puuid), riotApiKeyProvider.provide())
      .body()
      ?.profileIconId
}
