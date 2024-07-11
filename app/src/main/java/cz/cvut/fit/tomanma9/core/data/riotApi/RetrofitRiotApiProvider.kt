package cz.cvut.fit.tomanma9.core.data.riotApi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitRiotApiProvider {

  private val json = Json { ignoreUnknownKeys = true }

  @OptIn(ExperimentalSerializationApi::class)
  fun provide(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://no-url.com")
      .client(
        OkHttpClient.Builder()
          .addNetworkInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
          )
          .build()
      )
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }
}
