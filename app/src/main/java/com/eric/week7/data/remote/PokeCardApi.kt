package com.eric.week7.data.remote

import com.eric.week7.data.dto.PokeCardResponse
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://www.pokemonpricetracker.com/"


interface PokeCardApi {

    @GET("api/v2/cards")
    suspend fun getCards(
        @Query("cardType") cardType: String = "Pokemon",
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): PokeCardResponse

}