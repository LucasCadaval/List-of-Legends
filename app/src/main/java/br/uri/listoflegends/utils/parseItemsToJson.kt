package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ItemModel
import org.json.JSONArray
import org.json.JSONObject

fun parseItemsToJson(items: List<ItemModel>): String {
    val jsonArray = JSONArray()

    for (item in items) {
        val jsonObject = JSONObject().apply {
            put("name", item.name)
            put("description", item.description)

            // Converte o objeto Price para JSON
            val priceObject = JSONObject().apply {
                put("base", item.price.base)
                put("total", item.price.total)
                put("sell", item.price.sell)
            }
            put("price", priceObject)

            put("purchasable", item.purchasable)
            put("icon", item.icon)
        }
        jsonArray.put(jsonObject)
    }

    return jsonArray.toString()
}
