package br.uri.listoflegends.viewModels

import android.content.res.Configuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.uri.listoflegends.utils.Screen
import java.util.Locale

class MainViewModel : ViewModel() {
    private val _currentScreen = mutableStateOf<Screen>(Screen.Splash)
    val currentScreen: State<Screen> = _currentScreen

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun setLocale(languageCode: String, countryCode: String, resources: android.content.res.Resources) {
        val locale = Locale(languageCode, countryCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}