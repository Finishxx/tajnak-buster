package cz.cvut.fit.tomanma9.core.di

import com.google.firebase.analytics.FirebaseAnalytics
import cz.cvut.fit.tomanma9.core.data.riotApi.RetrofitRiotApiProvider
import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiDescription
import cz.cvut.fit.tomanma9.core.data.riotApi.RiotApiKeyProvider
import cz.cvut.fit.tomanma9.core.data.riotApi.api.AccountApi
import cz.cvut.fit.tomanma9.core.data.riotApi.api.SpectatorApi
import cz.cvut.fit.tomanma9.core.data.riotApi.api.SummonerApi
import cz.cvut.fit.tomanma9.core.domain.CheckGameStatusUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit

val coreModule = module {
  single { RetrofitRiotApiProvider.provide() }

  single { get<Retrofit>().create(RiotApiDescription::class.java) }

  singleOf(::AccountApi)
  singleOf(::SpectatorApi)
  singleOf(::SummonerApi)

  singleOf(::RiotApiKeyProvider)

  singleOf(::CheckGameStatusUseCase)

  singleOf(FirebaseAnalytics::getInstance)
}
