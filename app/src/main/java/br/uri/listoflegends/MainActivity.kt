package br.uri.listoflegends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.uri.champions.ui.theme.ListofLegendsTheme
import br.uri.listoflegends.ui.ChampionList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListofLegendsTheme {
                ChampionList()
            }
        }
    }
}