package cz.cvut.fit.tomanma9.core.data.riotApi.api

import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiDescription
import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiKeyProvider
import cz.cvut.fit.tomanma9.core.data.riotApi.getAccountUrl

class AccountApi(
  private val riotApiDescription: RiotApiDescription,
  private val keyProvider: RiotApiKeyProvider,
) {
  suspend fun puuidByNameTag(name: String, tag: String): String? =
    riotApiDescription.getAccount(getAccountUrl(name, tag), keyProvider.provide()).body()?.puuid
}
