package br.uri.listoflegends.services

import android.content.Context
import android.util.Log
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.utils.parseItemsFromJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

fun fetchItems(
    context: Context,
    callback: (Int, List<ItemModel>?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL("http://girardon.com.br:3001/items")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val responseCode = connection.responseCode

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            val items = parseItemsFromJson(response)

            Log.d("NetworkResponse", "Items Response Code: $responseCode, Items JSON: $response")

            callback(responseCode, items)
        } catch (e: Exception) {
            Log.e("NetworkError", "Erro ao buscar itens", e)
            callback(-1, null)
        }
    }
}
