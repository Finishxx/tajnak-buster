package cz.cvut.fit.tomanma9.features.summoner.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 2, entities = [DbSummoner::class, DbGame::class])
abstract class SummonerDatabase : RoomDatabase() {

  abstract fun summonerDao(): SummonerDao

  companion object {
    fun newInstance(context: Context): SummonerDatabase =
      Room.databaseBuilder(context, SummonerDatabase::class.java, "summoner.db").build()
  }
}
