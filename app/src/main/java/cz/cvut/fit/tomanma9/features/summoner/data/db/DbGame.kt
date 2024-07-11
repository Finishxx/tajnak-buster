package cz.cvut.fit.tomanma9.features.summoner.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game") data class DbGame(@PrimaryKey val id: Long)
