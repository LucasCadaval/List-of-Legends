package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel

fun randomizeChampionsAndItems(
    champions: List<ChampionModel>,
    items: List<ItemModel>,
    teamSize: Int = 5,
    itemsPerChampion: Int = 6
): Pair<List<Pair<ChampionModel, List<ItemModel>>>, List<Pair<ChampionModel, List<ItemModel>>>> {
    val shuffledChampions = champions.shuffled()
    val blueTeam = shuffledChampions.take(teamSize).map { champion ->
        champion to items.shuffled().take(itemsPerChampion)
    }
    val redTeam = shuffledChampions.drop(teamSize).take(teamSize).map { champion ->
        champion to items.shuffled().take(itemsPerChampion)
    }
    return blueTeam to redTeam
}
