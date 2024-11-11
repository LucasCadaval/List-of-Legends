package br.uri.listoflegends.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.services.fetchChampionsPage
import br.uri.listoflegends.utils.parseChampionsFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionListViewModel : ViewModel() {

    private val _champions = MutableLiveData<List<ChampionModel>?>()
    val champions: LiveData<List<ChampionModel>?> = _champions

    private val _displayedChampions = MutableLiveData<List<ChampionModel>>()
    val displayedChampions: LiveData<List<ChampionModel>> = _displayedChampions

    private val _responseCode = MutableLiveData<Int>()
    val responseCode: LiveData<Int> = _responseCode

    private val _hasMoreChampions = MutableLiveData<Boolean>()
    val hasMoreChampions: LiveData<Boolean> = _hasMoreChampions

    private var pageIndex = 0

    fun loadChampions(context: Context) {
        val championsFromPreference = SharedPreferencesManager.getChampions(context)
        val parsedChampions = championsFromPreference?.let {
            parseChampionsFromJson(it)
        }

        if (parsedChampions == null) {
            fetchChampions(context)
        } else {
            _champions.value = parsedChampions
            _displayedChampions.value = parsedChampions!!
        }
    }

    fun fetchChampions(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fetchChampionsPage(context, pageIndex, null) { code, response ->
                    _responseCode.postValue(code)
                    _champions.postValue(response)
                    _displayedChampions.postValue(response ?: listOf())
                    _hasMoreChampions.postValue(response?.isNotEmpty() == true)
                }
            }
        }
    }

    fun loadMoreChampions(context: Context) {
        pageIndex++
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                fetchChampionsPage(context, pageIndex, _displayedChampions.value) { code, response ->
                    _responseCode.postValue(code)
                    response?.let {
                        if (it.isNotEmpty()) {
                            _displayedChampions.postValue(_displayedChampions.value?.plus(it))
                            SharedPreferencesManager.savePageIndex(context, pageIndex)
                        } else {
                            SharedPreferencesManager.savePageIndex(context, pageIndex)
                            _hasMoreChampions.postValue(false)
                        }
                    }
                }
            }
        }
    }
}