package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ChampionModel

sealed class Screen {
    object Splash : Screen()
    object ChampionList : Screen()
    object ChampionDraft : Screen()
    data class ChampionDetail(val champion: ChampionModel) : Screen()
}