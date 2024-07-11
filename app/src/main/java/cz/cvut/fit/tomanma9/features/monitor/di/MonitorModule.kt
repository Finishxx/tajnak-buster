package cz.cvut.fit.tomanma9.features.monitor.di

import cz.cvut.fit.tomanma9.features.monitor.domain.NotifyOnNewGameUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val monitorModule = module { singleOf(::NotifyOnNewGameUseCase) }
