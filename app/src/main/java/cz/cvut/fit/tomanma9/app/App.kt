package cz.cvut.fit.tomanma9.app

import android.app.Application
import cz.cvut.fit.tomanma9.core.di.coreModule
import cz.cvut.fit.tomanma9.core.notification.di.notificationModule
import cz.cvut.fit.tomanma9.features.monitor.di.monitorModule
import cz.cvut.fit.tomanma9.features.monitor.worker.SchedulePeriodicBuster
import cz.cvut.fit.tomanma9.features.summoner.di.summonerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@App)
      modules(coreModule, summonerModule, notificationModule, monitorModule)
      SchedulePeriodicBuster(this@App)
    }
  }
}
