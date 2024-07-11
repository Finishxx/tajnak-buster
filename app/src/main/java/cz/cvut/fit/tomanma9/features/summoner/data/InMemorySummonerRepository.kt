package cz.cvut.fit.tomanma9.features.summoner.data

import cz.cvut.fit.tomanma9.core.model.Game
import cz.cvut.fit.tomanma9.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemorySummonerRepository : SummonerRepository {

  private val inGame = Game(0)
  private val notInGame = null

  private val tomat = Summoner("Finishx", "EUNE", "a", 0, inGame)
  private val majsner = Summoner("Harvey Specter", "ggez", "b", 0, inGame)
  private val daneken = Summoner("Monsterkybl", "GGEZ", "c", 0, notInGame)
  private val eliasosen = Summoner("LifeStopperCZ", "EUNE", "d", 0, notInGame)

  private val summoners =
    MutableStateFlow<List<Summoner>>(listOf(tomat, majsner, daneken, eliasosen))

  override fun getAllStream(): Flow<List<Summoner>> = summoners

  override suspend fun getAll(): List<Summoner> = summoners.value

  fun getAllTest(): List<Summoner> = summoners.value

  override suspend fun getByPuuid(puuid: String): Summoner? =
    summoners.value.firstOrNull { it.puuid == puuid }

  override suspend fun getByRiotId(name: String, tag: String): Summoner? =
    summoners.value.firstOrNull { it.name == name && it.tag == tag }

  override suspend fun removeByPuuid(puuid: String): Summoner? {
    val summoner: Summoner = getByPuuid(puuid) ?: return null

    summoners.value = summoners.value.minus(summoner)

    return summoner
  }

  override suspend fun insert(summoner: Summoner) = summoners.update { it + summoner }

  override suspend fun update(summoner: Summoner) {
    summoners.update {
      it.map { mapped -> if (mapped.puuid == summoner.puuid) summoner else mapped }
    }
  }
}
