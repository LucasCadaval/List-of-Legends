package br.uri.listoflegends.models

import java.util.Vector

data class ChampionModel(
    val id: String,
    val key: String?,
    val name: String,
    val title: String?,
    val tags: List<String?>,
    val stats: Stats?,
    val icon: String?,
    val sprite: Sprite?,
    val description: String?,
)

data class Sprite(
    val url: String,
    val x: Int,
    val y: Int
)

data class Stats(
    val hp: Int,
    val hpperlevel: Int,
    val mp: Int,
    val mpperlevel: Int,
    val movespeed: Int,
    val armor: Double,
    val armorperlevel: Double,
    val spellblock: Int,
    val spellblockperlevel: Double,
    val attackrange: Int,
    val hpregen: Double,
    val hpregenperlevel: Double,
    val mpregen: Int,
    val mpregenperlevel: Double,
    val crit: Int,
    val critperlevel: Int,
    val attackdamage: Double,
    val attackdamageperlevel: Double,
    val attackspeed: Double,
    val attackspeedperlevel: Double
)