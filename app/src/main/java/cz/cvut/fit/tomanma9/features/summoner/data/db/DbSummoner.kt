package cz.cvut.fit.tomanma9.features.summoner.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summoner")
data class DbSummoner(
  val name: String,
  val tag: String,
  @PrimaryKey val puuid: String,
  val iconId: Int,
  @Embedded val game: DbGame?,
)
