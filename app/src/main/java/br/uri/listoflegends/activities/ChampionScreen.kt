package br.uri.listoflegends.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.widget.Toast
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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.uri.listoflegends.R
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
    var mediaPlayer: MediaPlayer? = null

    val statLabels = listOf(
        stringResource(R.string.hp),
        stringResource(R.string.hp_per_level),
        stringResource(R.string.mp),
        stringResource(R.string.mp_per_level),
        stringResource(R.string.move_speed),
        stringResource(R.string.armor),
        stringResource(R.string.armor_per_level),
        stringResource(R.string.spell_block),
        stringResource(R.string.spell_block_per_level),
        stringResource(R.string.attack_range),
        stringResource(R.string.hp_regen),
        stringResource(R.string.hp_regen_per_level),
        stringResource(R.string.mp_regen),
        stringResource(R.string.mp_regen_per_level),
        stringResource(R.string.critical_chance),
        stringResource(R.string.critical_chance_per_level),
        stringResource(R.string.attack_damage),
        stringResource(R.string.attack_damage_per_level),
        stringResource(R.string.attack_speed),
        stringResource(R.string.attack_speed_per_level)
    )


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
                Spacer(modifier = Modifier.padding(vertical = 24.dp))
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
                                modifier = Modifier.fillMaxSize()
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

                        Row {
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
                                    contentDescription = stringResource(R.string.share),
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            val audioNotFoundString = stringResource(R.string.audio_not_found)
                            IconButton(
                                onClick = {
                                    val championAudioFileName = champion.name.lowercase().replace(" ", "_")

                                    val audioResId = context.resources.getIdentifier(championAudioFileName, "raw", context.packageName)

                                    if (audioResId != 0) {
                                        mediaPlayer = MediaPlayer.create(context, audioResId)
                                        mediaPlayer?.start()
                                        mediaPlayer?.setOnCompletionListener {
                                            it.release()
                                        }
                                    } else {
                                        Toast.makeText(context, audioNotFoundString, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color(gold),
                                    containerColor = Color.Transparent
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = stringResource(R.string.play_sound),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
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
                                text = stringResource(R.string.stats),
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(gold)
                            )
                        }

                        val statValues = listOf(
                            stats.hp,
                            stats.hpperlevel,
                            stats.mp,
                            stats.mpperlevel,
                            stats.movespeed,
                            stats.armor,
                            stats.armorperlevel,
                            stats.spellblock,
                            stats.spellblockperlevel,
                            stats.attackrange,
                            stats.hpregen,
                            stats.hpregenperlevel,
                            stats.mpregen,
                            stats.mpregenperlevel,
                            "${stats.crit}%",
                            "${stats.critperlevel}%",
                            stats.attackdamage,
                            stats.attackdamageperlevel,
                            stats.attackspeed,
                            stats.attackspeedperlevel
                        )
                        items(statLabels.zip(statValues)) { (label, value) ->
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