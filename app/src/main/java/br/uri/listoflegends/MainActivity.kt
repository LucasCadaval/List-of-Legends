package br.uri.listoflegends

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.Sprite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.Vector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListofLegendsTheme {
                ChampionListScreen()
            }
        }
    }
}

@Composable
fun ChampionListScreen() {
    var champions by remember { mutableStateOf(listOf<ChampionModel>()) }
    var searchQuery by remember { mutableStateOf("") }

    // Fetch champions data
    LaunchedEffect(Unit) {
        champions = fetchChampions()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fundo de céu estrelado
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

            // Filtered list of champions based on the search query
            val filteredChampions = if (searchQuery.isEmpty()) {
                champions
            } else {
                champions.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }

            // Grid with two columns showing champions
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                items(filteredChampions) { champion ->
                    ChampionCard(champion)
                }
            }
        }
    }
}

@Composable
fun ChampionCard(champion: ChampionModel) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Load image using coroutine
    LaunchedEffect(champion.icon) {
        coroutineScope.launch {
            bitmap = loadImageFromUrl(champion.icon)
        }
    }

    Card(
        onClick = { /* TODO: Handle click */ },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = champion.name,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = champion.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private suspend fun fetchChampions(): List<ChampionModel> {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("http://girardon.com.br:3001/champions")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                parseChampionsJson(response)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("NetworkError", "Failed to fetch champions", e)
            emptyList()
        }
    }
}

private fun parseChampionsJson(jsonString: String): List<ChampionModel> {
    val champions = mutableListOf<ChampionModel>()
    try {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val id = jsonObject.getString("id")
            val key = if (jsonObject.has("key")) jsonObject.getString("key") else null
            val name = jsonObject.getString("name")
            val title = if (jsonObject.has("title")) jsonObject.getString("title") else null
            val tags = Vector<String?>().apply {
                val tagsArray = jsonObject.optJSONArray("tags")
                if (tagsArray != null) {
                    for (j in 0 until tagsArray.length()) {
                        add(tagsArray.optString(j))
                    }
                }
            }
            val stats = if (jsonObject.has("stats")) jsonObject.getString("stats") else null
            val icon = if (jsonObject.has("icon")) jsonObject.getString("icon") else null
            val sprite = if (jsonObject.has("sprite")) {
                val spriteObject = jsonObject.getJSONObject("sprite")
                Sprite(
                    url = spriteObject.getString("url"),
                    x = spriteObject.getInt("x"),
                    y = spriteObject.getInt("y")
                )
            } else {
                null
            }
            val description = if (jsonObject.has("description")) jsonObject.getString("description") else null

            champions.add(
                ChampionModel(
                    id = id,
                    key = key,
                    name = name,
                    title = title,
                    tags = tags,
                    stats = stats,
                    icon = icon,
                    sprite = sprite,
                    description = description
                )
            )
        }
    } catch (e: Exception) {
        Log.e("JsonParseError", "Failed to parse champions JSON", e)
    }
    return champions
}

private suspend fun loadImageFromUrl(url: String?): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("ImageError", "Failed to load image", e)
            null
        }
    }
}
