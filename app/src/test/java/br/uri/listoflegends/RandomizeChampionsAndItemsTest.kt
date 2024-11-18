package br.uri.listoflegends

import android.util.Log
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.models.Price
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import br.uri.listoflegends.utils.randomizeChampionsAndItems
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RandomizeChampionsAndItemsTest {
    private lateinit var blueTeamWithItems: List<Pair<ChampionModel, List<ItemModel>>>
    private lateinit var redTeamWithItems: List<Pair<ChampionModel, List<ItemModel>>>

    @Before
    fun setUp(){
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
                icon = "icon_url1",
                sprite = Sprite(
                    url = "sprite_url1",
                    x = 10,
                    y = 20
                ),
                description = "Description1"
            ),
            ChampionModel(
                id = "2",
                key = "key2",
                name = "Champion2",
                title = "Title2",
                tags = listOf("Tag3", "Tag4"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url2",
                sprite = Sprite(
                    url = "sprite_url2",
                    x = 15,
                    y = 25
                ),
                description = "Description2"
            ),
            ChampionModel(
                id = "3",
                key = "key3",
                name = "Champion3",
                title = "Title3",
                tags = listOf("Tag5", "Tag6"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url3",
                sprite = Sprite(
                    url = "sprite_url3",
                    x = 20,
                    y = 30
                ),
                description = "Description3"
            ),
            ChampionModel(
                id = "4",
                key = "key4",
                name = "Champion4",
                title = "Title4",
                tags = listOf("Tag7", "Tag8"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url4",
                sprite = Sprite(
                    url = "sprite_url4",
                    x = 25,
                    y = 35
                ),
                description = "Description4"
            ),
            ChampionModel(
                id = "5",
                key = "key5",
                name = "Champion5",
                title = "Title5",
                tags = listOf("Tag9", "Tag10"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url5",
                sprite = Sprite(
                    url = "sprite_url5",
                    x = 30,
                    y = 40
                ),
                description = "Description5"
            ),
            // New champions
            ChampionModel(
                id = "6",
                key = "key6",
                name = "Champion6",
                title = "Title6",
                tags = listOf("Tag11", "Tag12"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url6",
                sprite = Sprite(
                    url = "sprite_url6",
                    x = 35,
                    y = 45
                ),
                description = "Description6"
            ),
            ChampionModel(
                id = "7",
                key = "key7",
                name = "Champion7",
                title = "Title7",
                tags = listOf("Tag13", "Tag14"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url7",
                sprite = Sprite(
                    url = "sprite_url7",
                    x = 40,
                    y = 50
                ),
                description = "Description7"
            ),
            ChampionModel(
                id = "8",
                key = "key8",
                name = "Champion8",
                title = "Title8",
                tags = listOf("Tag15", "Tag16"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url8",
                sprite = Sprite(
                    url = "sprite_url8",
                    x = 45,
                    y = 55
                ),
                description = "Description8"
            ),
            ChampionModel(
                id = "9",
                key = "key9",
                name = "Champion9",
                title = "Title9",
                tags = listOf("Tag17", "Tag18"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url9",
                sprite = Sprite(
                    url = "sprite_url9",
                    x = 50,
                    y = 60
                ),
                description = "Description9"
            ),
            ChampionModel(
                id = "10",
                key = "key10",
                name = "Champion10",
                title = "Title10",
                tags = listOf("Tag19", "Tag20"),
                stats = Stats(
                    hp = 500,
                    hpperlevel = 50,
                    mp = 90,
                    mpperlevel = 9,
                    movespeed = 340,
                    armor = 40.0,
                    armorperlevel = 4.0,
                    spellblock = 50,
                    spellblockperlevel = 5.0,
                    attackrange = 540,
                    hpregen = 7.0,
                    hpregenperlevel = 0.7,
                    mpregen = 5,
                    mpregenperlevel = 0.5,
                    crit = 4,
                    critperlevel = 0,
                    attackdamage = 80.0,
                    attackdamageperlevel = 5.0,
                    attackspeed = 0.9,
                    attackspeedperlevel = 0.04
                ),
                icon = "icon_url10",
                sprite = Sprite(
                    url = "sprite_url10",
                    x = 55,
                    y = 65
                ),
                description = "Description10"
            )
        )
        val items = listOf(
            ItemModel(
                name = "Item1",
                description = "Description1",
                price = Price(base = 300, total = 1000, sell = 700),
                purchasable = true,
                icon = "icon_url1"
            ),
            ItemModel(
                name = "Item2",
                description = "Description2",
                price = Price(base = 320, total = 1020, sell = 720),
                purchasable = true,
                icon = "icon_url2"
            ),
            ItemModel(
                name = "Item3",
                description = "Description3",
                price = Price(base = 340, total = 1040, sell = 740),
                purchasable = true,
                icon = "icon_url3"
            ),
            ItemModel(
                name = "Item4",
                description = "Description4",
                price = Price(base = 360, total = 1060, sell = 760),
                purchasable = true,
                icon = "icon_url4"
            ),
            ItemModel(
                name = "Item5",
                description = "Description5",
                price = Price(base = 380, total = 1080, sell = 780),
                purchasable = true,
                icon = "icon_url5"
            ),
            ItemModel(
                name = "Item6",
                description = "Description6",
                price = Price(base = 400, total = 1100, sell = 800),
                purchasable = true,
                icon = "icon_url6"
            ),
            ItemModel(
                name = "Item7",
                description = "Description7",
                price = Price(base = 420, total = 1120, sell = 820),
                purchasable = true,
                icon = "icon_url7"
            ),
            ItemModel(
                name = "Item8",
                description = "Description8",
                price = Price(base = 440, total = 1140, sell = 840),
                purchasable = true,
                icon = "icon_url8"
            ),
            ItemModel(
                name = "Item9",
                description = "Description9",
                price = Price(base = 460, total = 1160, sell = 860),
                purchasable = true,
                icon = "icon_url9"
            ),
            ItemModel(
                name = "Item10",
                description = "Description10",
                price = Price(base = 480, total = 1180, sell = 880),
                purchasable = true,
                icon = "icon_url10"
            ),
            ItemModel(
                name = "Item11",
                description = "Description11",
                price = Price(base = 500, total = 1200, sell = 900),
                purchasable = true,
                icon = "icon_url11"
            ),
            ItemModel(
                name = "Item12",
                description = "Description12",
                price = Price(base = 520, total = 1220, sell = 920),
                purchasable = true,
                icon = "icon_url12"
            ),
            ItemModel(
                name = "Item13",
                description = "Description13",
                price = Price(base = 540, total = 1240, sell = 940),
                purchasable = true,
                icon = "icon_url13"
            ),
            ItemModel(
                name = "Item14",
                description = "Description14",
                price = Price(base = 560, total = 1260, sell = 960),
                purchasable = true,
                icon = "icon_url14"
            ),
            ItemModel(
                name = "Item15",
                description = "Description15",
                price = Price(base = 580, total = 1280, sell = 980),
                purchasable = true,
                icon = "icon_url15"
            ),
            ItemModel(
                name = "Item16",
                description = "Description16",
                price = Price(base = 600, total = 1300, sell = 1000),
                purchasable = true,
                icon = "icon_url16"
            ),
            ItemModel(
                name = "Item17",
                description = "Description17",
                price = Price(base = 620, total = 1320, sell = 1020),
                purchasable = true,
                icon = "icon_url17"
            ),
            ItemModel(
                name = "Item18",
                description = "Description18",
                price = Price(base = 640, total = 1340, sell = 1040),
                purchasable = true,
                icon = "icon_url18"
            ),
            ItemModel(
                name = "Item19",
                description = "Description19",
                price = Price(base = 660, total = 1360, sell = 1060),
                purchasable = true,
                icon = "icon_url19"
            ),
            ItemModel(
                name = "Item20",
                description = "Description20",
                price = Price(base = 680, total = 1380, sell = 1080),
                purchasable = true,
                icon = "icon_url20"
            )
        )
        val (tempBlueTeam, tempRedTeam) = randomizeChampionsAndItems(champions, items)
        blueTeamWithItems = tempBlueTeam
        redTeamWithItems = tempRedTeam

    }
    @Test
    fun testRandomizeChampionsAndItems() {

        val blueTeam = blueTeamWithItems.map { it.first }
        val redTeam = redTeamWithItems.map { it.first }

        assertEquals(5, blueTeam.size)
        assertEquals(5, redTeam.size)

    }
    @Test
    fun testNumberOfItems(){
        blueTeamWithItems.forEach { (_, championItems) ->
            assertEquals(6, championItems.size)
        }
        redTeamWithItems.forEach { (_, championItems) ->
            assertEquals(6, championItems.size)
        }
    }
    @Test
    fun testDifferentChampions(){
        val allChampions = blueTeamWithItems.map { it.first } + redTeamWithItems.map { it.first }
        assertEquals(10, allChampions.distinct().size)

    }
}