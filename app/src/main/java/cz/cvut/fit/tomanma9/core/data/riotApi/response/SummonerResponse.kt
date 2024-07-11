package cz.cvut.fit.tomanma9.core.data.riotApi.response

import kotlinx.serialization.Serializable

@Serializable
data class SummonerResponse(val accountId: String, val profileIconId: Int, val id: String)
