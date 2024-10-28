package br.uri.listoflegends.ui

import android.provider.CalendarContract.Colors
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.uri.champions.ui.theme.BlueLol
import br.com.uri.champions.ui.theme.GoldLol
import br.uri.listoflegends.R
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampions
import br.uri.listoflegends.utils.parseChampionsFromJson

@Composable
fun ChampionDraft() {
    var responseCode by remember { mutableStateOf(-1) }

    var blueTeam by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }
    var redTeam by remember { mutableStateOf<List<ChampionModel>>(emptyList()) }

    val context = LocalContext.current
//    SharedPreferencesManager.clearChampions(context)
    val championsFromPreference = SharedPreferencesManager.getChampions(context)
    val parsedChampions = championsFromPreference?.let {
        parseChampionsFromJson(it)
    }

    var champions by remember { mutableStateOf<List<ChampionModel>?>(parsedChampions) }

    if (parsedChampions == null) {
        LaunchedEffect(Unit) {
            fetchChampions(context) { code, response ->
                responseCode = code
                champions = response
            }
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (champions == null) {
                Text(stringResource(id = R.string.loading), color = GoldLol)
            } else {
                Button(onClick = {
                    val shuffledChampions = champions!!.shuffled()
                    blueTeam = shuffledChampions.take(5)
                    redTeam = shuffledChampions.drop(5).take(5)
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(2.dp, GoldLol, shape = RoundedCornerShape(16.dp))
                        .height(36.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BlueLol)) {
                    Text(stringResource(id = R.string.sort_teams), color = GoldLol)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextBlue()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                ) {
                    items(blueTeam) { champion ->
                        ChampionCard(champion, { null })
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextRed()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                ) {
                    items(redTeam) { champion ->
                        ChampionCard(champion, { null })
                    }
                }
            }
        }
    }
}

@Composable
fun OutlinedTextBlue() {
    Box {
        Text(
            text = stringResource(id = R.string.blue_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(1.dp, 1.dp)
        )
        Text(
            text = stringResource(id = R.string.blue_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(-1.dp, 1.dp)
        )
        Text(
            text = stringResource(id = R.string.blue_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(1.dp, -1.dp)
        )
        Text(
            text = stringResource(id = R.string.blue_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(-1.dp, -1.dp)
        )
        Text(
            text = stringResource(id = R.string.blue_team),
            style = TextStyle(
                color = Color.Blue,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun OutlinedTextRed() {
    Box {
        Text(
            text = stringResource(id = R.string.red_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(1.dp, 1.dp)
        )
        Text(
            text = stringResource(id = R.string.red_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(-1.dp, 1.dp)
        )
        Text(
            text = stringResource(id = R.string.red_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(1.dp, -1.dp)
        )
        Text(
            text = stringResource(id = R.string.red_team),
            style = TextStyle(
                color = GoldLol,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.offset(-1.dp, -1.dp)
        )
        Text(
            text = stringResource(id = R.string.red_team),
            style = TextStyle(
                color = Color.Red,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
