package br.uri.listoflegends

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.models.ChampionModel
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             ListofLegendsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var responseCode by remember { mutableStateOf(-1) }
    var jsonResponse by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        fetchResponseCode { code, response ->
            responseCode = code
            jsonResponse = response ?: "Error fetching data"
        }
    }

    Text(
        text = "Hello $name! Response Code: $responseCode\nJSON: $jsonResponse",
        modifier = modifier
    )
}


private fun fetchResponseCode(callback: (Int, String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL("http://girardon.com.br:3001/champions")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            Log.d("NetworkResponse", "Response Code: $responseCode, Response JSON: $response")

            callback(responseCode, response)
        } catch (e: Exception) {
            Log.e("NetworkError", "Failed to fetch response", e)
            callback(-1, null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListofLegendsTheme {
        Greeting("Android")
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChampionCard(champion: ChampionModel){
    val context = LocalContext.current
    var bitmap: Bitmap? = null

    CoroutineScope(Dispatchers.IO).launch {
        bitmap = imageFromUrl(champion.icon)
        withContext(Dispatchers.Main) {
            // Trigger recomposition here if needed
        }
    }

    Card(onClick = { /*TODO*/ }) {
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = champion.name)
        }
        Text(text = champion.name)
    }

}

private fun imageFromUrl(url: String?): Bitmap? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        null
    }
}


