package br.uri.listoflegends.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.utils.parseChampionsFromJson
import br.uri.listoflegends.utils.parseChampionsToJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


fun fetchChampionsPage(
    context: Context,
    page: Int,
    champions: List<ChampionModel>?,
    callback: (Int, List<ChampionModel>?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL("http://girardon.com.br:3001/champions?page=$page")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            val newChampions = parseChampionsFromJson(response)

            Log.d("TAG", "fetchChampionsPage: Page: $page, Code: $responseCode, JSON: $response, newChampions: $newChampions")

            val allChampions = if (champions != null) {
                val updatedChampions = champions + newChampions
                Log.d("TAG", "allChampions: ${updatedChampions.size}")
                parseChampionsToJson(updatedChampions)
            } else {
                parseChampionsToJson(newChampions)
            }


            SharedPreferencesManager.saveChampions(context, allChampions)

            Log.d("NetworkResponse", "Page: $page, Code: $responseCode, JSON: $response")

            callback(responseCode, newChampions)
        } catch (e: Exception) {
            Log.e("NetworkError", "Erro", e)
            callback(-1, null)
        }
    }
}


suspend fun getImageFromUrl(url: String?): Bitmap? {
    return withContext(Dispatchers.IO) {
        var bitmap: Bitmap? = null
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val input = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
            input.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        bitmap
    }

}
