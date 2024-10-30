package br.uri.listoflegends.services

import android.content.Context
import android.content.SharedPreferences
import br.uri.listoflegends.models.ChampionModel

object SharedPreferencesManager {
    private const val PREF_NAME = "CHAMPION_PREF"
    private const val CHAMPIONS = "CHAMPIONS"
    private const val PAGE_INDEX = "page_index"

    fun savePageIndex(context: Context, pageIndex: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(PAGE_INDEX, pageIndex)
            apply()
        }
    }

    fun getPageIndex(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(PAGE_INDEX, 1) // Default to 1 if not found
    }

    fun saveChampions(context: Context, champions: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(CHAMPIONS, champions)
            apply()
        }
    }

    fun getChampions(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(CHAMPIONS, null)
    }

    fun clearChampions(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(CHAMPIONS)
            apply()
        }
    }
}