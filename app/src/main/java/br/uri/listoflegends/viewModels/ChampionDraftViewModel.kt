package br.uri.listoflegends.viewModels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampionsPage
import br.uri.listoflegends.services.fetchItems
import br.uri.listoflegends.services.getImageFromUrl
import br.uri.listoflegends.utils.parseChampionsFromJson
import br.uri.listoflegends.utils.parseItemsFromJson
import br.uri.listoflegends.utils.parseItemsToJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionDraftViewModel(application: Application) : AndroidViewModel(application) {

    var responseCode: Int = -1
    var blueTeam: List<Pair<ChampionModel, Int>> = emptyList()
    var redTeam: List<Pair<ChampionModel, Int>> = emptyList()
    var items: List<ItemModel> = emptyList()
    var champions: List<ChampionModel>? = null

    private val context = application.applicationContext

    init {
        loadItems()
        loadChampions()
    }

    private fun loadItems() {
        val itemsFromPreference = SharedPreferencesManager.getItems(context)
        if (itemsFromPreference == null) {
            fetchItems(context) { code, apiItems ->
                if (code == 200 && apiItems != null) {
                    val itemsJson = parseItemsToJson(apiItems)
                    SharedPreferencesManager.saveItems(context, itemsJson)
                    items = apiItems
                }
            }
        } else {
            items = parseItemsFromJson(itemsFromPreference)
        }
    }

    private fun loadChampions() {
        val championsFromPreference = SharedPreferencesManager.getChampions(context)
        val parsedChampions = championsFromPreference?.let { parseChampionsFromJson(it) }
        if (parsedChampions == null) {
            fetchChampionsPage(context, 1, null) { code, response ->
                responseCode = code
                champions = response
            }
        } else {
            champions = parsedChampions
        }
    }

    fun shuffleTeams(positionIcons: List<Int>): List<ChampionModel>{
        champions?.let {
            val shuffledChampions = it.shuffled()
            blueTeam = shuffledChampions.take(5).zip(positionIcons.shuffled())
            redTeam = shuffledChampions.drop(5).take(5).zip(positionIcons.shuffled())
        }
        return champions!!
    }

    fun getChampionBitmap(champion: ChampionModel, callback: (Bitmap?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = getImageFromUrl(champion.icon)
            callback(bitmap)
        }
    }
}
