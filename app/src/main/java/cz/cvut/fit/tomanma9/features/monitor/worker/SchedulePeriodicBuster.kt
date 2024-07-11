package cz.cvut.fit.tomanma9.features.monitor.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private val PERIODIC_TAJNAK_BUSTER_WORKER_NAME = "PeriodicBuster"

fun SchedulePeriodicBuster(context: Context) {
  val periodicWorkRequest =
    PeriodicWorkRequestBuilder<PeriodicBuster>(15L, TimeUnit.MINUTES).build()

  WorkManager.getInstance(context)
    .enqueueUniquePeriodicWork(
      PERIODIC_TAJNAK_BUSTER_WORKER_NAME,
      ExistingPeriodicWorkPolicy.KEEP,
      periodicWorkRequest,
    )
}
