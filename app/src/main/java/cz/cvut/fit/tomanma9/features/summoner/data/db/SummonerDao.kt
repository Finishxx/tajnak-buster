package cz.cvut.fit.tomanma9.features.summoner.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SummonerDao {

  @Query("SELECT * FROM summoner") fun getSummonersStream(): Flow<List<DbSummoner>>

  @Query("SELECT * FROM summoner WHERE puuid = :puuid")
  suspend fun getSummoner(puuid: String): DbSummoner?

  @Query("SELECT * FROM summoner WHERE (name = :name AND tag = :tag)")
  suspend fun getByRiotId(name: String, tag: String): DbSummoner?

  @Insert suspend fun insert(summoner: DbSummoner)

  @Insert suspend fun insertAll(summoners: List<DbSummoner>)

  @Query("DELETE FROM summoner WHERE puuid = :puuid") suspend fun delete(puuid: String)

  @Update suspend fun update(summoner: DbSummoner)
}
