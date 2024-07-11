package cz.cvut.fit.tomanma9.features.summoner.data

import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.features.summoner.data.db.SummonerLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RoomSummonerRepository(private val localDataSource: SummonerLocalDataSource) :
  SummonerRepository {
  override fun getAllStream(): Flow<List<Summoner>> = localDataSource.getSummonersStream()

  override suspend fun getAll(): List<Summoner> = localDataSource.getSummonersStream().first()

  override suspend fun getByPuuid(puuid: String): Summoner? = localDataSource.getSummoner(puuid)

  override suspend fun getByRiotId(name: String, tag: String): Summoner? =
    localDataSource.getByRiotId(name, tag)

  override suspend fun removeByPuuid(puuid: String): Summoner? {
    val summoner = getByPuuid(puuid) ?: return null

    localDataSource.delete(puuid)

    return summoner
  }

  override suspend fun insert(summoner: Summoner) = localDataSource.insert(summoner)

  override suspend fun update(summoner: Summoner) = localDataSource.update(summoner)
}
