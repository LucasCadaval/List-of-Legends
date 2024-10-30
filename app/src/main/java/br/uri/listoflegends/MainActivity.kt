package br.uri.listoflegends

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.activities.ChampionScreen
import br.uri.listoflegends.ui.ChampionList
import br.uri.listoflegends.ui.SplashScreen
import br.uri.listoflegends.ui.TopBar
import br.uri.listoflegends.ui.ChampionDraft
import br.uri.listoflegends.utils.Screen
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.setContent
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.services.SharedPreferencesManager
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val locale = Locale.getDefault()
        val languageCode = locale.language
        val countryCode = locale.country
        setLocale(languageCode, countryCode)
        setLocale("pt", "BR")
         SharedPreferencesManager.clearChampions(context)
         SharedPreferencesManager.clearPageIndex(context)


        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        setContent {
            ListofLegendsTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

                Scaffold(
                    topBar = {
                        if (currentScreen is Screen.ChampionDetail || currentScreen is Screen.ChampionDraft) {
                            TopBar(
                                onBackPressed = {
                                    currentScreen = Screen.ChampionList
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    when (currentScreen) {
                        is Screen.Splash -> SplashScreen {
                            currentScreen = Screen.ChampionList
                        }
                        is Screen.ChampionList -> ChampionList(
                            onChampionClick = { champion ->
                                currentScreen = Screen.ChampionDetail(champion)
                            },
                            onTeamDraftClick = { currentScreen = Screen.ChampionDraft}
                        )
                        is Screen.ChampionDetail -> {
                            val champion = (currentScreen as Screen.ChampionDetail).champion
                            ChampionScreen(champion)
                        }
                        is Screen.ChampionDraft -> ChampionDraft()
                    }
                }
            }
        }
    }
    private fun setLocale(languageCode: String, countryCode: String) {
        val locale = Locale(languageCode, countryCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}