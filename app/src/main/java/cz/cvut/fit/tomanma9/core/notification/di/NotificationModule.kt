package cz.cvut.fit.tomanma9.core.notification.di

import cz.cvut.fit.tomanma9.core.notification.NotificationHelper
import org.koin.dsl.module

val notificationModule = module { single { NotificationHelper() } }
