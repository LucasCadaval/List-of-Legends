package br.uri.listoflegends.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.getImageFromUrl
import br.uri.listoflegends.ui.TopBar
import br.uri.listoflegends.utils.Screen
import br.uri.listoflegends.utils.formatChampionForSharing
import br.uri.listoflegends.utils.share
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChampionScreen(champion: ChampionModel) {
    val coroutineScope = rememberCoroutineScope()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val gold = 0xFFC89B3C
    val context = LocalContext.current

    LaunchedEffect(champion.icon) {
        coroutineScope.launch(Dispatchers.IO) {
            bitmap = getImageFromUrl(champion.icon)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A1428))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer( modifier = Modifier.padding(vertical = 24.dp) )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(125.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Color(0xFFD4AF37), RoundedCornerShape(10.dp))
                    ) {
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = champion.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = champion.name,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            ),
                            color = Color.White,
                        )

                        champion.title?.let { title ->
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color(gold),
                            )
                        }

                        IconButton(
                            onClick = {
                                val formattedChampion = formatChampionForSharing(champion)
                                share(context, formattedChampion, bitmap)
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = Color(gold),
                                containerColor = Color.Transparent
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    champion.description?.let { description ->
                        item {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                            )
                        }
                    }

                    champion.stats?.let { stats ->
                        item {
                            Text(
                                text = "Stats:",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(gold)
                            )
                        }

                        val statLabels = listOf(
                            "HP" to stats.hp,
                            "HP per Level" to stats.hpperlevel,
                            "MP" to stats.mp,
                            "MP per Level" to stats.mpperlevel,
                            "Move Speed" to stats.movespeed,
                            "Armor" to stats.armor,
                            "Armor per Level" to stats.armorperlevel,
                            "Spell Block" to stats.spellblock,
                            "Spell Block per Level" to stats.spellblockperlevel,
                            "Attack Range" to stats.attackrange,
                            "HP Regen" to stats.hpregen,
                            "HP Regen per Level" to stats.hpregenperlevel,
                            "MP Regen" to stats.mpregen,
                            "MP Regen per Level" to stats.mpregenperlevel,
                            "Critical Chance" to "${stats.crit}%",
                            "Critical Chance per Level" to "${stats.critperlevel}%",
                            "Attack Damage" to stats.attackdamage,
                            "Attack Damage per Level" to stats.attackdamageperlevel,
                            "Attack Speed" to stats.attackspeed,
                            "Attack Speed per Level" to stats.attackspeedperlevel
                        )

                        items(statLabels) { (label, value) ->
                            Text(
                                text = " • $label: $value",
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
