package br.uri.listoflegends.viewModels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.getImageFromUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChampionViewModel : ViewModel() {

    private val _bitmap = MutableLiveData<Bitmap?>()
    val bitmap: LiveData<Bitmap?> = _bitmap

    fun loadChampionImage(champion: ChampionModel) {
        viewModelScope.launch {
            val image = withContext(Dispatchers.IO) {
                getImageFromUrl(champion.icon)
            }
            _bitmap.postValue(image)
        }
    }
}