package br.uri.listoflegends.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.uri.listoflegends.R
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.fetchChampions

@Composable
fun ChampionList(onChampionClick: (ChampionModel) -> Unit) {
    var champions by remember { mutableStateOf<List<ChampionModel>?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var responseCode by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        fetchChampions { code, response ->
            responseCode = code
            champions = response
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(modifier = Modifier.fillMaxSize()) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (champions == null) {
                Text("Loading...")
            } else {
                val filteredChampions = if (searchQuery.isEmpty()) {
                    champions
                } else {
                    champions!!.filter { it.name.contains(searchQuery, ignoreCase = true) }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(filteredChampions!!) { champion ->
                        ChampionCard(champion, onChampionClick)
                    }
                }
            }
        }
    }
}
