package br.uri.listoflegends.services

import android.content.Context
import android.content.SharedPreferences
import br.uri.listoflegends.models.ChampionModel

object SharedPreferencesManager {
    private const val PREF_NAME = "CHAMPION_PREF"
    private const val CHAMPIONS = "CHAMPIONS"

    fun saveChampions(context: Context, champions: List<ChampionModel>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(CHAMPIONS, champions.toString())
            apply()
        }
    }

    fun getChampions(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(CHAMPIONS, null)
    }
}