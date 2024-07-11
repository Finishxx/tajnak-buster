package cz.cvut.fit.tomanma9.features.summoner.data.db

import cz.cvut.fit.tomanma9.core.model.Game
import cz.cvut.fit.tomanma9.core.model.Summoner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SummonerLocalDataSource(private val summonerDao: SummonerDao) {

  private fun DbSummoner.toSummoner(): Summoner =
    Summoner(puuid = puuid, name = name, tag = tag, iconId = iconId, game = game.toGame())

  private fun Summoner.toDbSummoner(): DbSummoner =
    DbSummoner(puuid = puuid, name = name, tag = tag, iconId = iconId, game = game.toDbGame())

  private fun Game?.toDbGame(): DbGame? {
    this ?: return null

    return DbGame(id = id)
  }

  private fun DbGame?.toGame(): Game? {
    this ?: return null

    return Game(id = id)
  }

  fun getSummonersStream(): Flow<List<Summoner>> =
    summonerDao.getSummonersStream().map { it.map { dbSummoner -> dbSummoner.toSummoner() } }

  suspend fun getSummoner(puuid: String): Summoner? = summonerDao.getSummoner(puuid)?.toSummoner()

  suspend fun getByRiotId(name: String, tag: String): Summoner? =
    summonerDao.getByRiotId(name, tag)?.toSummoner()

  suspend fun insert(summoner: Summoner) {
    summonerDao.insert(summoner.toDbSummoner())
  }

  suspend fun insertAll(summoners: List<Summoner>) {
    summonerDao.insertAll(summoners.map { it.toDbSummoner() })
  }

  suspend fun delete(puuid: String) {
    summonerDao.delete(puuid)
  }

  suspend fun update(summoner: Summoner) {
    summonerDao.update(summoner.toDbSummoner())
  }
}
