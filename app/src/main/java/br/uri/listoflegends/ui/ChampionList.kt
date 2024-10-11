package br.uri.listoflegends.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.uri.champions.ui.theme.BlueLol
import br.com.uri.champions.ui.theme.GoldLol
import br.uri.listoflegends.R
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampions
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ChampionList(onChampionClick: (ChampionModel) -> Unit) {
    var champions by remember { mutableStateOf<List<ChampionModel>?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var responseCode by remember { mutableStateOf(-1) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        fetchChampions { code, response ->
            responseCode = code
            champions = response
            // salva champions no "localstorage"
            SharedPreferencesManager.saveChampions(context, champions!!)
            // pega champions no "localstorage"
            // SharedPreferencesManager.getChampions(context, champions!!)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val alpha = remember { Animatable(0f) }
        Image(
            painter = painterResource(id = R.drawable.rift),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .background(BlueLol)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, GoldLol, shape = RoundedCornerShape(16.dp))
                    .background(BlueLol, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                if (searchQuery.isEmpty()) {
                    Text(
                        text = "Pesquisar...",
                        color = Color.Gray,
                    )
                }

                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            }

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
