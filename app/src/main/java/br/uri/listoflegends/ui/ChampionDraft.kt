package br.uri.listoflegends.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.fetchChampions

@Composable
fun ChampionDraft() {
    var champions by remember { mutableStateOf<List<ChampionModel>?>(null) }
    var responseCode by remember { mutableStateOf(-1) }

    var blueTeam by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var redTeam by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        fetchChampions { code, response ->
            responseCode = code
            champions = response
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (champions == null) {
            Text("Loading champions...")
        } else {
            Button(onClick = {
                val shuffledChampions = champions!!.shuffled()
                blueTeam = shuffledChampions.take(5)
                redTeam = shuffledChampions.drop(5).take(5)
            }) {
                Text("Sortear Times")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Time Azul", color = Color.Blue)
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(blueTeam) { champion ->
                    ChampionCard(champion)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Time Vermelho", color = Color.Red)
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(redTeam) { champion ->
                    ChampionCard(champion)
                }
            }
        }
    }
}

@Composable
fun ChampionCard(champion: ChampionModel) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
    ) {
        Text(text = champion.name, modifier = Modifier.padding(16.dp))
    }
}
