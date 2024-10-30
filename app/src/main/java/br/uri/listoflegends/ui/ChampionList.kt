package br.uri.listoflegends.ui

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.uri.champions.ui.theme.BlueLol
import br.com.uri.champions.ui.theme.GoldLol
import br.uri.listoflegends.R
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampionsPage
import br.uri.listoflegends.utils.parseChampionsFromJson
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ChampionList(onTeamDraftClick: () -> Unit, onChampionClick: (ChampionModel) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var responseCode by remember { mutableStateOf(-1) }
    val context = LocalContext.current
    val championsFromPreference = SharedPreferencesManager.getChampions(context)
    val parsedChampions = championsFromPreference?.let {
        parseChampionsFromJson(it)
    }

    var champions by remember { mutableStateOf<List<ChampionModel>?>(parsedChampions) }
    var displayedChampions by remember { mutableStateOf<List<ChampionModel>>(listOf()) }
    var pageIndex by remember { mutableStateOf(SharedPreferencesManager.getPageIndex(context)) }
    var hasMoreChampions by remember { mutableStateOf(true) }

    if (parsedChampions == null) {
        LaunchedEffect(Unit) {
            fetchChampionsPage(context, pageIndex) { code, response ->
                responseCode = code
                champions = response
                displayedChampions = response?.take(20) ?: listOf()
                hasMoreChampions = response?.isNotEmpty() == true
            }
        }
    } else {
        if (displayedChampions.isEmpty()) {
            displayedChampions = parsedChampions.take(20)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        text = stringResource(id = R.string.search_hint),
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

            Button(
                onClick = {
                    onTeamDraftClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, GoldLol, shape = RoundedCornerShape(16.dp))
                    .height(36.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BlueLol)
            ) {
                Text(text = stringResource(R.string.team_draft), color = GoldLol)
            }

            if (champions == null) {
                Text(stringResource(id = R.string.loading), color = Color.White)
            } else {
                val filteredChampions = if (searchQuery.isEmpty()) {
                    displayedChampions
                } else {
                    displayedChampions.filter { it.name.contains(searchQuery, ignoreCase = true) }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                ) {
                    items(filteredChampions) { champion ->
                        ChampionCard(champion, onChampionClick)
                    }

                    if (hasMoreChampions) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Button(
                                onClick = {
                                    pageIndex++
                                    fetchChampionsPage(context, pageIndex) { code, response ->
                                        responseCode = code
                                        response?.let {
                                            if (it.isNotEmpty()) {
                                                displayedChampions = displayedChampions + it.take(20)
                                                SharedPreferencesManager.savePageIndex(context, pageIndex)
                                            } else {
                                                SharedPreferencesManager.savePageIndex(context, pageIndex)
                                                hasMoreChampions = false
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = "Carregar mais", color = GoldLol)
                            }
                        }
                    }
                }
            }
        }
    }
}