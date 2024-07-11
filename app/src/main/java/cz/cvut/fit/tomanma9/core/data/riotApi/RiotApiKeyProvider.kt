package cz.cvut.fit.tomanma9.core.data.riotApi

import cz.cvut.fit.tomanma9.BuildConfig

class RiotApiKeyProvider {

  fun provide(): String = BuildConfig.RIOT_API_KEY
}
