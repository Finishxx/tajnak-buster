package cz.cvut.fit.tomanma9.features.monitor.domain

import android.content.Context
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.core.domain.CheckGameStatusUseCase
import cz.cvut.fit.tomanma9.core.model.Game
import cz.cvut.fit.tomanma9.core.model.Summoner
import cz.cvut.fit.tomanma9.core.notification.NotificationHelper
import cz.cvut.fit.tomanma9.features.summoner.data.RoomSummonerRepository

/**
 * Update Summoner game data. If summoner was last not seen in game and currently he is, send
 * notification.
 */
class NotifyOnNewGameUseCase(
  private val summonerRepository: RoomSummonerRepository,
  private val checkGameStatus: CheckGameStatusUseCase,
  private val notificationHelper: NotificationHelper,
  private val applicationContext: Context,
) {

  suspend operator fun invoke() {

    val summoners = summonerRepository.getAll()
    val busted: MutableList<Summoner> = mutableListOf()

    for (summoner in summoners) {
      val game = checkGameStatus(summoner)

      val updatedSummoner = updateSummonerGameStatus(summoner, game)

      val wasInGame = summoner.game != null
      val isInGame = game != null

      if (wasInGame.not().and(isInGame)) {
        busted.add(updatedSummoner)
      }
    }

    if (busted.isNotEmpty()) {
      notificationHelper.showProgressNotification(
        applicationContext.getString(R.string.notification_title),
        "${busted.map { it.name }}" + applicationContext.getString(R.string.notification_after_list),
      )
    }
  }

  private suspend fun updateSummonerGameStatus(summoner: Summoner, currentGame: Game?): Summoner {
    if (summoner.game == currentGame) {
      return summoner
    }

    val updatedSummoner = summoner.copy(game = currentGame)
    summonerRepository.update(updatedSummoner)
    return updatedSummoner
  }
}
