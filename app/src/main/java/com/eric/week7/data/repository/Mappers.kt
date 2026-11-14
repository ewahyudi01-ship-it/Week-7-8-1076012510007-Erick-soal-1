package com.eric.week7.data.repository

import com.eric.week7.data.dto.PokeCardDto
import com.eric.week7.model.PokeCard

fun PokeCardDto.toPokeCard(): PokeCard {
    return PokeCard(
        imageUrl = imageUrl,
        name = name,
        minPrice = prices.low,
        maxPrice = prices.market,
        listings = prices.listings,
        sellers = prices.sellers,
        primaryPrinting = prices.primaryPrinting,
        cardNumber = cardNumber,
        rarity = rarity,
        pokemonType = pokemonType
    )
}