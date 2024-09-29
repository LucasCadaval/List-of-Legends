package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ChampionModel

sealed class Screen {
    object ChampionList : Screen()
    data class ChampionDetail(val champion: ChampionModel) : Screen()
}