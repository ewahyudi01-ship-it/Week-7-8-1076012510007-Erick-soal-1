package com.eric.week7.data.dto

import com.google.gson.annotations.SerializedName


data class PokeCardResponse(
    val data: List<PokeCardDto>
)

data class PokeCardDto(
    val name: String,
    val cardNumber: String,
    val rarity: String,
    val pokemonType: String,

    @SerializedName("imageCdnUrl")
    val imageUrl: String,

    val prices: PokePricesDto
)

data class PokePricesDto(
    val market: Double,
    val low: Double,
    val listings: Int,
    val sellers: Int,
    val primaryPrinting: String
)