package cz.cvut.fit.tomanma9.features.summoner.data

import cz.cvut.fit.tomanma9.core.model.Summoner
import kotlinx.coroutines.flow.Flow

interface SummonerRepository {
  fun getAllStream(): Flow<List<Summoner>>

  suspend fun getAll(): List<Summoner>

  suspend fun getByPuuid(puuid: String): Summoner?

  suspend fun getByRiotId(name: String, tag: String): Summoner?

  suspend fun removeByPuuid(puuid: String): Summoner?

  suspend fun insert(summoner: Summoner)

  suspend fun update(summoner: Summoner)
}
