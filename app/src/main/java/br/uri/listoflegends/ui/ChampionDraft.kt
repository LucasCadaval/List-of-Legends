package br.uri.listoflegends.ui

import android.graphics.Bitmap
import android.provider.CalendarContract.Colors
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.uri.champions.ui.theme.BlueLol
import br.com.uri.champions.ui.theme.GoldLol
import br.uri.listoflegends.R
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampionsPage
import br.uri.listoflegends.services.fetchItems
import br.uri.listoflegends.services.getImageFromUrl
import br.uri.listoflegends.utils.parseChampionsFromJson
import br.uri.listoflegends.utils.parseItemsFromJson
import br.uri.listoflegends.utils.parseItemsToJson
import br.uri.listoflegends.viewModels.ChampionDraftViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChampionDraft(viewModel: ChampionDraftViewModel = viewModel()) {
    val context = LocalContext.current

    var blueTeam by remember { mutableStateOf<List<Pair<ChampionModel, Int>>>(viewModel.blueTeam) }
    var redTeam by remember { mutableStateOf<List<Pair<ChampionModel, Int>>>(viewModel.redTeam) }
    var champions by remember { mutableStateOf<List<ChampionModel>?>(viewModel.champions) }

    val positionIcons = listOf(
        R.drawable.position_challenger_mid,
        R.drawable.position_challenger_top,
        R.drawable.position_challenger_bot,
        R.drawable.position_challenger_support,
        R.drawable.position_challenger_jungle
    )

    LaunchedEffect(Unit) {
        // You can trigger any actions you want when the composable is first loaded
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.champions == null) {
                Text(stringResource(id = R.string.loading), color = GoldLol)
            } else {
                Button(
                    onClick = {
                        viewModel.shuffleTeams(positionIcons)
                        blueTeam = viewModel.blueTeam
                        redTeam = viewModel.redTeam
                        Log.d("ChampionDraft", "Teams shuffled and state updated")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(2.dp, GoldLol, shape = RoundedCornerShape(16.dp))
                        .height(36.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BlueLol)
                ) {
                    Text(stringResource(id = R.string.sort_teams), color = GoldLol)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.blue_team),
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(redTeam) { (champion, positionIcon) ->
                        val randomItems = viewModel.items.shuffled().take(6)
                        RandomCard(champion = champion, items = randomItems, positionIcon = positionIcon, viewModel = viewModel)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.red_team),
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(redTeam) { (champion, positionIcon) ->
                        val randomItems = viewModel.items.shuffled().take(6)
                        RandomCard(champion = champion, items = randomItems, positionIcon = positionIcon, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun RandomCard(champion: ChampionModel, items: List<ItemModel>, positionIcon: Int, viewModel: ChampionDraftViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(champion.icon) {
        viewModel.getChampionBitmap(champion) { fetchedBitmap ->
            bitmap = fetchedBitmap
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(BlueLol, shape = RoundedCornerShape(8.dp))
            .border(2.dp, GoldLol, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = champion.name,
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } ?: Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = champion.name.take(1),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = champion.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = GoldLol,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = positionIcon),
                    contentDescription = "Posição",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items.forEach { item ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberAsyncImagePainter(model = item.icon),
                            contentDescription = item.name,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}