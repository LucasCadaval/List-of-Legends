package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import org.json.JSONArray
import java.util.Vector

fun parseChampionsFromJson(jsonResponse: String): List<ChampionModel> {
    val championsList = mutableListOf<ChampionModel>()

    val jsonArray = JSONArray(jsonResponse)
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)

        val id = jsonObject.getString("id")
        val key = jsonObject.optString("key", null)
        val name = jsonObject.getString("name")
        val title = jsonObject.optString("title", null)

        val tagsArray = jsonObject.optJSONArray("tags")
        val tags = Vector<String?>().apply {
            for (j in 0 until (tagsArray?.length() ?: 0)) {
                add(tagsArray?.optString(j))
            }
        }

        val statsObject = jsonObject.optJSONObject("stats")
        val stats = statsObject?.let {
            Stats(
                hp = it.getInt("hp"),
                hpperlevel = it.getInt("hpperlevel"),
                mp = it.getInt("mp"),
                mpperlevel = it.getInt("mpperlevel"),
                movespeed = it.getInt("movespeed"),
                armor = it.getDouble("armor"),
                armorperlevel = it.getDouble("armorperlevel"),
                spellblock = it.getInt("spellblock"),
                spellblockperlevel = it.getDouble("spellblockperlevel"),
                attackrange = it.getInt("attackrange"),
                hpregen = it.getDouble("hpregen"),
                hpregenperlevel = it.getDouble("hpregenperlevel"),
                mpregen = it.getInt("mpregen"),
                mpregenperlevel = it.getDouble("mpregenperlevel"),
                crit = it.getInt("crit"),
                critperlevel = it.getInt("critperlevel"),
                attackdamage = it.getDouble("attackdamage"),
                attackdamageperlevel = it.getDouble("attackdamageperlevel"),
                attackspeed = it.getDouble("attackspeed"),
                attackspeedperlevel = it.getDouble("attackspeedperlevel")
            )
        }

        val icon = jsonObject.optString("icon", null)

        val spriteObject = jsonObject.optJSONObject("sprite")
        val sprite = spriteObject?.let {
            Sprite(
                url = it.getString("url"),
                x = it.getInt("x"),
                y = it.getInt("y")
            )
        }

        val description = jsonObject.optString("description", null)

        val champion = ChampionModel(
            id = id,
            key = key,
            name = name,
            title = title,
            tags = tags,
            stats = stats,
            icon = icon,
            sprite = sprite,
            description = description
        )

        championsList.add(champion)
    }

    return championsList
}
