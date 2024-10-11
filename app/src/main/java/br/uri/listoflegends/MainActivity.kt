package br.uri.listoflegends

import android.Manifest
import android.annotation.SuppressLint
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
import br.uri.listoflegends.utils.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

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
                        if (currentScreen is Screen.ChampionDetail) {
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
                            }
                        )
                        is Screen.ChampionDetail -> {
                            val champion = (currentScreen as Screen.ChampionDetail).champion
                            ChampionScreen(champion)
                        }
                    }
                }
            }
        }
    }
}