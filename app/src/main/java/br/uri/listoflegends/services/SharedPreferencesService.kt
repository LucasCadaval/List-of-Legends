package br.uri.listoflegends.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.utils.parseItemsFromJson
import br.uri.listoflegends.utils.parseItemsToJson

object SharedPreferencesManager {
    const val PREF_NAME = "CHAMPION_PREF"
    const val CHAMPIONS = "CHAMPIONS"
    const val ITEMS = "ITEMS"
    const val PAGE_INDEX = "page_index"

    fun savePageIndex(context: Context, pageIndex: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(PAGE_INDEX, pageIndex)
            apply()
        }
    }

    fun getPageIndex(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(PAGE_INDEX, 1)
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

    fun saveItems(context: Context, items: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(ITEMS, items)
            apply()
        }
        Log.d("SharedPreferencesManager", "Itens salvos: $items") // Confirme que os itens foram salvos
    }


    fun getItems(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val itemsJson = sharedPreferences.getString(ITEMS, null)
        Log.d("SharedPreferencesManager", "Itens carregados: $itemsJson") // Log para verificar o carregamento dos itens
        return itemsJson
    }


    fun clearChampions(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(CHAMPIONS)
            apply()
        }
    }

    fun clearItems(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(ITEMS)
            apply()
        }
    }

    fun clearPageIndex(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(PAGE_INDEX)
            apply()
        }
    }
}
