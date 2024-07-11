package cz.cvut.fit.tomanma9.core.data.riotApi

import cz.cvut.fit.tomanma9.core.data.riotApi.response.AccountResponse
import cz.cvut.fit.tomanma9.core.data.riotApi.response.SpectatorResponse
import cz.cvut.fit.tomanma9.core.data.riotApi.response.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

const val EUROPE_BASE_URL: String = "https://europe.api.riotgames.com"

const val EUNE_BASE_URL: String = "https://eun1.api.riotgames.com"

const val RIOT_KEY_HEADER_NAME: String = "X-Riot-Token"

fun getAccountUrl(gameName: String, tagLine: String) =
  "$EUROPE_BASE_URL/riot/account/v1/accounts/by-riot-id/$gameName/$tagLine"

fun getCurrentGameUrl(puuid: String) =
  "$EUNE_BASE_URL/lol/spectator/v5/active-games/by-summoner/$puuid"

fun getSummonerUrl(puuid: String) = "$EUNE_BASE_URL/lol/summoner/v4/summoners/by-puuid/$puuid"

interface RiotApiDescription {
  @GET
  suspend fun getAccount(
    @Url url: String,
    @Header(RIOT_KEY_HEADER_NAME) key: String,
  ): Response<AccountResponse>

  @GET
  suspend fun getCurrentGame(
    @Url url: String,
    @Header(RIOT_KEY_HEADER_NAME) key: String,
  ): Response<SpectatorResponse>

  @GET
  suspend fun getSummoner(
    @Url url: String,
    @Header(RIOT_KEY_HEADER_NAME) key: String,
  ): Response<SummonerResponse>
}
