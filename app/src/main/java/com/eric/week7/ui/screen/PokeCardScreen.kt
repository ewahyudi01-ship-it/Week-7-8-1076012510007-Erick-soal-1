
package com.eric.week7.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.eric.week7.model.PokeCard
import com.eric.week7.ui.PokeCardUiState
import com.eric.week7.viewmodel.PokeCardViewModel

@Composable
fun PokeCardScreen(
    onCardClick: (PokeCard) -> Unit,
    viewModel: PokeCardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(20,20,20))
            .padding(16.dp).padding(top=20.dp)
    ) {

        Row (
            Modifier.fillMaxWidth()
                .background(Color(24,31,42),RoundedCornerShape(15.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD32F2F)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CatchingPokemon,
                        contentDescription = "PokéTracker Logo",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Column (
                    modifier = Modifier.padding(start = 14.dp)
                ) {
                    Text("PokéTracker",
                        fontSize = 21.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)

                    Text("Real-time TCG Card Valuation",
                        fontSize = 11.6.sp,
                        color = Color.Gray)
                }
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(22,54,55))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(7.dp),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = "poin",
                    tint = Color(16,185,129)
                )
                Text(
                    text = " Live API",
                    color = Color(17,153,110),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A202C))
                .border(
                    width = 1.dp,
                    color = Color(0xFF283141),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(23.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "",
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear",
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        when (val state = uiState) {
            is PokeCardUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PokeCardUiState.Success -> {
                if (state.cards.isEmpty()) {
                    Text("Belum ada kartu Pokémon.")
                } else {
                    LazyRow (
                        modifier = Modifier.padding(horizontal = 7.dp)
                    ) {
                        items(state.cards){ card ->
                            CategoryRow(card)
                        }
                    }
                    Text("Market Results (${state.cards.size})",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp)

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 12.dp)
                    ) {
                        items(state.cards) { card ->
                            PokeCardContent(
                                card = card,
                                onClick = {
                                    onCardClick(card)
                                }
                            )
                        }
                    }
                }
            }

            is PokeCardUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Connection Failed",
                        modifier = Modifier.padding(20.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color(242,184,181))

                    Text(uiState.toString(),
                        modifier = Modifier.padding(bottom = 20.dp),
                        color = Color.Gray)

                    Button(
                        onClick = { viewModel.loadCards() },
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(
                            horizontal = 25.dp,
                            vertical = 5.dp
                        ),
                        colors = ButtonDefaults.buttonColors(Color(229,57,53))
                    ) {
                        Text("Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryRow(card: PokeCard) {
    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(34,43,56))
            .padding(horizontal = 8.dp)

    ) {
        Text(
            text = " ${card.name}",
            color = Color(140,153,169),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PokeCardContent(
    card: PokeCard,
    onClick: () -> Unit
) {
    val cardNameClean = card.name.replace("(", "").replace(")", "").trim()
    val cardPrefix = card.cardNumber.filter { it.isLetter() }
    val cleanNumber = card.cardNumber.replace("/", "").trim()
    val cleanType = card.rarity.replace("s", "").trim()

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(24,31,42)),
        border = BorderStroke(2.dp, Color(27,34,46)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = card.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column{
                Text(
                    text = "${cardNameClean} - ${cleanNumber}",
                    fontSize = 17.6.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text("${cardPrefix} ${card.rarity} • #${cleanNumber}",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold)

                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(23,56,56))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = "Trending Up",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = " $${card.maxPrice}",
                            color = Color(17,153,110),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(34,43,56))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = " ${cleanType}",
                            color = Color(140,153,169),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = false)
@Composable
fun preview () {
    PokeCardScreen(
        onCardClick = {}
    )
}
