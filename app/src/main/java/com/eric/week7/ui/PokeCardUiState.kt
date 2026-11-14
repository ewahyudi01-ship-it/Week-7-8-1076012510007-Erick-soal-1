package com.eric.week7.ui

import com.eric.week7.model.PokeCard

sealed interface PokeCardUiState {

    object Loading : PokeCardUiState
    data class Success(val cards: List<PokeCard>) : PokeCardUiState
    data class Error(val message: String) : PokeCardUiState
}