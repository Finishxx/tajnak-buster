package cz.cvut.fit.tomanma9.features.monitor.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import cz.cvut.fit.tomanma9.features.monitor.domain.NotifyOnNewGameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PeriodicBuster(context: Context, params: WorkerParameters) :
  CoroutineWorker(context, params), KoinComponent {

  private val notifyOnNewGameUseCase: NotifyOnNewGameUseCase by inject()
  private val analytics: FirebaseAnalytics by inject()

  override suspend fun doWork(): Result {
    return withContext(Dispatchers.IO) {
      notifyOnNewGameUseCase()
      analytics.logEvent("Periodic buster") {}

      Result.success()
    }
  }
}
