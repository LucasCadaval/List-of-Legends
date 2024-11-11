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
import androidx.activity.viewModels
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.services.SharedPreferencesManager
import br.uri.listoflegends.viewModels.MainViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val locale = Locale.getDefault()
        val languageCode = locale.language
        val countryCode = locale.country
        viewModel.setLocale(languageCode, countryCode, resources)

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
                val currentScreen by viewModel.currentScreen

                Scaffold(
                    topBar = {
                        if (currentScreen is Screen.ChampionDetail || currentScreen is Screen.ChampionDraft) {
                            TopBar(
                                onBackPressed = {
                                    viewModel.setCurrentScreen(Screen.ChampionList)
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    when (currentScreen) {
                        is Screen.Splash -> SplashScreen {
                            viewModel.setCurrentScreen(Screen.ChampionList)
                        }
                        is Screen.ChampionList -> ChampionList(
                            onChampionClick = { champion ->
                                viewModel.setCurrentScreen(Screen.ChampionDetail(champion))
                            },
                            onTeamDraftClick = { viewModel.setCurrentScreen(Screen.ChampionDraft) }
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
}