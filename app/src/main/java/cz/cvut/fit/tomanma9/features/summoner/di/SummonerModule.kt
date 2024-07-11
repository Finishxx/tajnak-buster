package cz.cvut.fit.tomanma9.features.summoner.di

import cz.cvut.fit.tomanma9.features.summoner.data.RoomSummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.data.SummonerRepository
import cz.cvut.fit.tomanma9.features.summoner.data.db.SummonerDatabase
import cz.cvut.fit.tomanma9.features.summoner.data.db.SummonerLocalDataSource
import cz.cvut.fit.tomanma9.features.summoner.domain.VerifySummonerUseCase
import cz.cvut.fit.tomanma9.features.summoner.ui.add.AddViewModel
import cz.cvut.fit.tomanma9.features.summoner.ui.detail.DetailViewModel
import cz.cvut.fit.tomanma9.features.summoner.ui.list.ListViewModel
import cz.cvut.fit.tomanma9.features.summoner.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val summonerModule = module {
  single { SummonerDatabase.newInstance(androidContext()) }

  single { get<SummonerDatabase>().summonerDao() }

  singleOf(::SummonerLocalDataSource)

  singleOf(::RoomSummonerRepository)

  single<SummonerRepository> { get<RoomSummonerRepository>() }

  singleOf(::VerifySummonerUseCase)

  viewModelOf(::AddViewModel)
  viewModelOf(::DetailViewModel)
  viewModelOf(::ListViewModel)
  viewModelOf(::SearchViewModel)
}
