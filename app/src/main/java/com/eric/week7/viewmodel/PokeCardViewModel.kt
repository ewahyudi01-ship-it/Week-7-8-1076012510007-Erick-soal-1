package com.eric.week7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eric.week7.data.remote.ServiceLocator
import com.eric.week7.data.repository.PokeCardRepository
import com.eric.week7.model.PokeCard
import com.eric.week7.ui.PokeCardUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PokeCardViewModel : ViewModel() {
    private val repository = PokeCardRepository(
        ServiceLocator.pokeCardApi
    )

    private val _uiState =
        MutableStateFlow<PokeCardUiState>(PokeCardUiState.Loading)
    val uiState: StateFlow<PokeCardUiState> = _uiState

    private val _selectedCard =
        MutableStateFlow<PokeCard?>(null)

    val selectedCard: StateFlow<PokeCard?> = _selectedCard

    fun selectCard(card: PokeCard) {
        _selectedCard.value = card
    }

    init {
        loadCards()
    }

    fun loadCards() {
        viewModelScope.launch {
            _uiState.value = PokeCardUiState.Loading

            try {
                val cards = repository.getCards()

                _uiState.value = PokeCardUiState.Success(cards)

            } catch (e: CancellationException) {
                throw e

            }  catch (e: HttpException) {
                val errorMessage = "HTTP ${e.code()}"

                _uiState.value = PokeCardUiState.Error(errorMessage)
            }
            catch (e: Exception) {
                _uiState.value = PokeCardUiState.Error(
                    "Connection Failed"
                )
            }
        }
    }
}
