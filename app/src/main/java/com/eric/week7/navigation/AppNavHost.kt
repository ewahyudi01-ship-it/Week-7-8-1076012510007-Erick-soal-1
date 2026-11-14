package com.eric.week7.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eric.week7.model.PokeCard
import com.eric.week7.ui.screen.PokeCardDetailScreen
import com.eric.week7.ui.screen.PokeCardScreen
import com.eric.week7.viewmodel.PokeCardViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: PokeCardViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = PokeCardRoute
    ) {
        composable<PokeCardRoute> {
            PokeCardScreen(
                viewModel = viewModel,
                onCardClick = { card ->
                    viewModel.selectCard(card)
                    navController.navigate(
                        PokeCardDetailRoute
                    )
                }
            )
        }

        composable<PokeCardDetailRoute> {
            val selectedCard by
            viewModel.selectedCard.collectAsStateWithLifecycle()

            selectedCard?.let { card ->
                PokeCardDetailScreen(
                    card = card,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
