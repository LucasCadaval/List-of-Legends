package br.uri.listoflegends.utils

import br.uri.listoflegends.models.ChampionModel
import org.json.JSONArray
import org.json.JSONObject
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
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


fun parseChampionsToJson(champions: List<ChampionModel>): String {
    val jsonArray = JSONArray()

    for (champion in champions) {
        val jsonObject = JSONObject()

        jsonObject.put("id", champion.id)
        jsonObject.put("key", champion.key)
        jsonObject.put("name", champion.name)
        jsonObject.put("title", champion.title)

        val tagsArray = JSONArray()
        champion.tags?.forEach { tag ->
            tagsArray.put(tag)
        }
        jsonObject.put("tags", tagsArray)

        champion.stats?.let {
            val statsObject = JSONObject()
            statsObject.put("hp", it.hp)
            statsObject.put("hpperlevel", it.hpperlevel)
            statsObject.put("mp", it.mp)
            statsObject.put("mpperlevel", it.mpperlevel)
            statsObject.put("movespeed", it.movespeed)
            statsObject.put("armor", it.armor)
            statsObject.put("armorperlevel", it.armorperlevel)
            statsObject.put("spellblock", it.spellblock)
            statsObject.put("spellblockperlevel", it.spellblockperlevel)
            statsObject.put("attackrange", it.attackrange)
            statsObject.put("hpregen", it.hpregen)
            statsObject.put("hpregenperlevel", it.hpregenperlevel)
            statsObject.put("mpregen", it.mpregen)
            statsObject.put("mpregenperlevel", it.mpregenperlevel)
            statsObject.put("crit", it.crit)
            statsObject.put("critperlevel", it.critperlevel)
            statsObject.put("attackdamage", it.attackdamage)
            statsObject.put("attackdamageperlevel", it.attackdamageperlevel)
            statsObject.put("attackspeed", it.attackspeed)
            statsObject.put("attackspeedperlevel", it.attackspeedperlevel)
            jsonObject.put("stats", statsObject)
        }

        jsonObject.put("icon", champion.icon)

        champion.sprite?.let {
            val spriteObject = JSONObject()
            spriteObject.put("url", it.url)
            spriteObject.put("x", it.x)
            spriteObject.put("y", it.y)
            jsonObject.put("sprite", spriteObject)
        }

        jsonObject.put("description", champion.description)

        jsonArray.put(jsonObject)
    }

    return jsonArray.toString()
}