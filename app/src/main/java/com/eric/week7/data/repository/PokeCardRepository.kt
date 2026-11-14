package com.eric.week7.data.repository

import com.eric.week7.data.remote.PokeCardApi
import com.eric.week7.model.PokeCard

class PokeCardRepository(private val api: PokeCardApi) {

    suspend fun getCards(): List<PokeCard> {
        val response = api.getCards()

        return response.data.map { dto ->
            dto.toPokeCard()
        }
    }
}