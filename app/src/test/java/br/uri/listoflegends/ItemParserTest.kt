package br.uri.listoflegends

import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.models.Price
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import br.uri.listoflegends.utils.parseChampionsFromJson
import br.uri.listoflegends.utils.parseChampionsToJson
import br.uri.listoflegends.utils.parseItemsFromJson
import br.uri.listoflegends.utils.parseItemsToJson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class ItemParserJsonTest {

    private lateinit var itemsJson: String

    @Before
    fun setUp() {
        itemsJson = """
            [
                {
                    "name": "Item1",
                    "description": "Description1",
                    "price": {
                        "base": 300,
                        "total": 1000,
                        "sell": 700
                    },
                    "purchasable": true,
                    "icon": "icon_url"
                }
            ]
        """.trimIndent()
    }

    @Test
    fun testParseItemsFromJson() {
        val items = parseItemsFromJson(itemsJson)
        assertEquals(1, items.size)
        val item = items[0]
        assertEquals("Item1", item.name)
        assertEquals("Description1", item.description)
        assertEquals(300, item.price.base)
        assertEquals(1000, item.price.total)
        assertEquals(700, item.price.sell)
        assertEquals(true, item.purchasable)
        assertEquals("icon_url", item.icon)
    }

    @Test
    fun testParseItemsToJson() {
        val items = listOf(
            ItemModel(
                name = "Item1",
                description = "Description1",
                price = Price(base = 300, total = 1000, sell = 700),
                purchasable = true,
                icon = "icon_url"
            )
        )

        val json = parseItemsToJson(items)
        val expectedJson = "[{\"name\":\"Item1\",\"description\":\"Description1\",\"price\":{\"base\":300,\"total\":1000,\"sell\":700},\"purchasable\":true,\"icon\":\"icon_url\"}]"

        assertEquals(expectedJson, json)
    }
}