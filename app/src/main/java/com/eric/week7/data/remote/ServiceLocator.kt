package com.eric.week7.data.remote

import com.eric.week7.BuildConfig
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                ApiKeyInterceptor(BuildConfig.POKEMON_API_KEY)
            ).build()
    }

    val pokeCardApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.pokemonpricetracker.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeCardApi::class.java)
    }
}

