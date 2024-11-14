package br.uri.listoflegends

import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import br.uri.listoflegends.utils.parseChampionsFromJson
import br.uri.listoflegends.utils.parseChampionsToJson
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class ChampionParserJsonTest {

    private lateinit var championsJson: String

    @Before
    fun setUp() {
        championsJson = """
            [
                {
                    "id": "1",
                    "key": "key1",
                    "name": "Champion1",
                    "title": "Title1",
                    "tags": ["Tag1", "Tag2"],
                    "stats": {
                        "hp": 100,
                        "hpperlevel": 10,
                        "mp": 50,
                        "mpperlevel": 5,
                        "movespeed": 300,
                        "armor": 20.0,
                        "armorperlevel": 2.0,
                        "spellblock": 30,
                        "spellblockperlevel": 3.0,
                        "attackrange": 500,
                        "hpregen": 5.0,
                        "hpregenperlevel": 0.5,
                        "mpregen": 3,
                        "mpregenperlevel": 0.3,
                        "crit": 0,
                        "critperlevel": 0,
                        "attackdamage": 60.0,
                        "attackdamageperlevel": 3.0,
                        "attackspeed": 0.7,
                        "attackspeedperlevel": 0.02
                    },
                    "icon": "icon_url",
                    "sprite": {
                        "url": "sprite_url",
                        "x": 10,
                        "y": 20
                    },
                    "description": "Description1"
                }
            ]
        """.trimIndent()
    }

    @Test
    fun testParseChampionsFromJson() {
        val champions = parseChampionsFromJson(championsJson)
        assertEquals(1, champions.size)
        val champion = champions[0]
        assertEquals("1", champion.id)
        assertEquals("key1", champion.key)
        assertEquals("Champion1", champion.name)
        assertEquals("Title1", champion.title)
        assertEquals(listOf("Tag1", "Tag2"), champion.tags)
        assertEquals(100, champion.stats?.hp)
        assertEquals(10, champion.stats?.hpperlevel)
        assertEquals(50, champion.stats?.mp)
        assertEquals(5, champion.stats?.mpperlevel)
        assertEquals(300, champion.stats?.movespeed)
        champion.stats?.armor?.let { assertEquals(20.0, it, 0.0) }
        champion.stats?.armorperlevel?.let { assertEquals(2.0, it, 0.0) }
        assertEquals(30, champion.stats?.spellblock)
        champion.stats?.spellblockperlevel?.let { assertEquals(3.0, it, 0.0) }
        assertEquals(500, champion.stats?.attackrange)
        champion.stats?.hpregen?.let { assertEquals(5.0, it, 0.0) }
        champion.stats?.hpregenperlevel?.let { assertEquals(0.5, it, 0.0) }
        assertEquals(3, champion.stats?.mpregen)
        champion.stats?.mpregenperlevel?.let { assertEquals(0.3, it, 0.0) }
        assertEquals(0, champion.stats?.crit)
        assertEquals(0, champion.stats?.critperlevel)
        champion.stats?.attackdamage?.let { assertEquals(60.0, it, 0.0) }
        champion.stats?.attackdamageperlevel?.let { assertEquals(3.0, it, 0.0) }
        champion.stats?.attackspeed?.let { assertEquals(0.7, it, 0.0) }
        champion.stats?.attackspeedperlevel?.let { assertEquals(0.02, it, 0.0) }
        assertEquals("icon_url", champion.icon)
        assertEquals("sprite_url", champion.sprite?.url)
        assertEquals(10, champion.sprite?.x)
        assertEquals(20, champion.sprite?.y)
        assertEquals("Description1", champion.description)
    }

    @Test
    fun testParseChampionsToJson() {
        val champions = listOf(
            ChampionModel(
                id = "1",
                key = "key1",
                name = "Champion1",
                title = "Title1",
                tags = listOf("Tag1", "Tag2"),
                stats = Stats(
                    hp = 100,
                    hpperlevel = 10,
                    mp = 50,
                    mpperlevel = 5,
                    movespeed = 300,
                    armor = 20.0,
                    armorperlevel = 2.0,
                    spellblock = 30,
                    spellblockperlevel = 3.0,
                    attackrange = 500,
                    hpregen = 5.0,
                    hpregenperlevel = 0.5,
                    mpregen = 3,
                    mpregenperlevel = 0.3,
                    crit = 0,
                    critperlevel = 0,
                    attackdamage = 60.0,
                    attackdamageperlevel = 3.0,
                    attackspeed = 0.7,
                    attackspeedperlevel = 0.02
                ),
                icon = "icon_url",
                sprite = Sprite(
                    url = "sprite_url",
                    x = 10,
                    y = 20
                ),
                description = "Description1"
            )
        )

        val json = parseChampionsToJson(champions)
        val expectedJson = "[{\"id\":\"1\",\"key\":\"key1\",\"name\":\"Champion1\",\"title\":\"Title1\",\"tags\":[\"Tag1\",\"Tag2\"],\"stats\":{\"hp\":100,\"hpperlevel\":10,\"mp\":50,\"mpperlevel\":5,\"movespeed\":300,\"armor\":20,\"armorperlevel\":2,\"spellblock\":30,\"spellblockperlevel\":3,\"attackrange\":500,\"hpregen\":5,\"hpregenperlevel\":0.5,\"mpregen\":3,\"mpregenperlevel\":0.3,\"crit\":0,\"critperlevel\":0,\"attackdamage\":60,\"attackdamageperlevel\":3,\"attackspeed\":0.7,\"attackspeedperlevel\":0.02},\"icon\":\"icon_url\",\"sprite\":{\"url\":\"sprite_url\",\"x\":10,\"y\":20},\"description\":\"Description1\"}]"

        assertEquals(expectedJson, json)
    }
}