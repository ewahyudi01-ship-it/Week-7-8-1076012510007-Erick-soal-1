package com.eric.week7.model

data class PokeCard(
    val imageUrl: String,
    val name: String,
    val minPrice: Double,
    val maxPrice: Double,
    val listings: Int,
    val sellers: Int,
    val primaryPrinting: String,
    val cardNumber: String,
    val rarity: String,
    val pokemonType: String
)