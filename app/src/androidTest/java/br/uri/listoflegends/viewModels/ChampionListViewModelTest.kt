package br.uri.listoflegends.viewModels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import br.uri.listoflegends.services.SharedPreferencesManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [34])
@RunWith(MockitoJUnitRunner::class)
class ChampionListViewModelTest {
    @Mock
    private var context: Context = ApplicationProvider.getApplicationContext()

    @Mock
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var viewModel: ChampionListViewModel

    private val championModel = ChampionModel(
        "aatrox", "The Darkin Blade",
        name = "Aatrox",
        title = "The Darkin Blade",
        tags = listOf("Fighter", "Tank"),
        stats = Stats(
            hp = 580,
            hpperlevel = 90,
            mp = 0,
            mpperlevel = 0,
            movespeed = 345,
            armor = 38.0,
            armorperlevel = 3.25,
            spellblock = 32,
            spellblockperlevel = 1.25,
            attackrange = 175,
            hpregen = 3.0,
            hpregenperlevel = 1.0,
            mpregen = 0,
            mpregenperlevel = 0.0,
            crit = 0,
            critperlevel = 0,
            attackdamage = 60.0,
            attackdamageperlevel = 5.0,
            attackspeed = 0.651,
            attackspeedperlevel = 2.5
        ),
        icon = "http://ddragon.leagueoflegends.com/cdn/11.16.1/img/champion/Aatrox.png",
        sprite = Sprite(
            url = "http://ddragon.leagueoflegends.com/cdn/11.16.1/img/sprite/spell0.png",
            x = 0,
            y = 0
        ),
        description = "Aatrox is a legendary warrior, one of only five that remain"
    )

    @Before
    fun setup() {
        Mockito.`when`(sharedPreferencesManager.getChampions(context)).thenReturn("{\"champions\":[{\"name\":\"$championModel.name\",\"title\":\"$championModel.title\"}]}")
        Mockito.`when`(sharedPreferencesManager.savePageIndex(context, 2))
        viewModel = ChampionListViewModel()
    }

    @Test
    fun testloadChampionswithcachedchampions() = runBlockingTest {
        val observer = Mockito.mock(Observer::class.java) as Observer<List<ChampionModel>?>
        viewModel.champions.observeForever(observer)

        viewModel.loadChampions(context)

        Mockito.verify(observer).onChanged(listOf(championModel))
    }

    @Test
    fun testloadChampionswithoutcachedchampions() = runBlockingTest {
        Mockito.`when`(sharedPreferencesManager.getChampions(context)).thenReturn(null)
        val observer = Mockito.mock(Observer::class.java) as Observer<List<ChampionModel>?>
        viewModel.champions.observeForever(observer)

        viewModel.loadChampions(context)

        Mockito.verify(observer).onChanged(listOf(championModel))
    }

    @Test
    fun testfetchChampions() = runBlockingTest {
        val observer = Mockito.mock(Observer::class.java) as Observer<Int>
        viewModel.responseCode.observeForever(observer)

        viewModel.fetchChampions(context)

        Mockito.verify(observer).onChanged(200)
    }

    @Test
    fun testloadMoreChampions() = runBlockingTest {
        val observer = Mockito.mock(Observer::class.java) as Observer<List<ChampionModel>>
        viewModel.displayedChampions.observeForever(observer)

        viewModel.loadMoreChampions(context)

        Mockito.verify(observer).onChanged(listOf(championModel, championModel))
    }

    @Test
    fun testloadMoreChampionswithoutmorechampions() = runBlockingTest {
        Mockito.`when`(sharedPreferencesManager.getChampions(context)).thenReturn("{\"champions\":[]}")
        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        viewModel.hasMoreChampions.observeForever(observer)

        viewModel.loadMoreChampions(context)

        Mockito.verify(observer).onChanged(false)
    }
}
