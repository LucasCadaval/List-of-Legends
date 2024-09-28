package br.uri.listoflegends.models

import java.util.Vector

data class ChampionModel(
    val id: String,
    val key: String?,
    val name: String,
    val title: String?,
    val tags: Vector<String?>,
    val stats: String?,
    val icon: String?,
    val sprite: Sprite?,
    val description: String?,
)

data class Sprite(
    val url: String,
    val x: Int,
    val y: Int
)