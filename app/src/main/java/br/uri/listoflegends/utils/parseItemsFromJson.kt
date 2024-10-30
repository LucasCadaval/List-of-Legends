package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.models.Price
import org.json.JSONArray
import org.json.JSONObject

fun parseItemsFromJson(json: String): List<ItemModel> {
    val items = mutableListOf<ItemModel>()
    val jsonArray = JSONArray(json)

    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)

        val priceObject = jsonObject.getJSONObject("price")
        val price = Price(
            base = priceObject.getInt("base"),
            total = priceObject.getInt("total"),
            sell = priceObject.getInt("sell")
        )

        val item = ItemModel(
            name = jsonObject.getString("name"),
            description = jsonObject.getString("description"),
            price = price,
            purchasable = jsonObject.getBoolean("purchasable"),
            icon = jsonObject.getString("icon")
        )

        items.add(item)
    }
    return items
}
