package br.uri.listoflegends

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.ui.ChampionList
import br.uri.listoflegends.activities.ChampionScreen
import br.uri.listoflegends.utils.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SoundTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun clickOnAatroxAndPlaySound() {
        val currentScreen = mutableStateOf<Screen>(Screen.ChampionList)

        composeTestRule.setContent {
            when (val screen = currentScreen.value) {
                is Screen.ChampionList -> {
                    ChampionList(
                        onTeamDraftClick = {},
                        onChampionClick = { champion ->
                            currentScreen.value = Screen.ChampionDetail(champion)
                        }
                    )
                }
                is Screen.ChampionDetail -> {
                    ChampionScreen(champion = screen.champion)
                }
                else -> {
                }
            }
        }

        composeTestRule.waitUntil(30000){
            composeTestRule.onAllNodes(hasText(context.getString(R.string.loading))).fetchSemanticsNodes().isEmpty()
        }

        composeTestRule
            .onNodeWithText("Aatrox")
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithTag("SoundIcon")
            .assertIsDisplayed()
            .performClick()

    }
}
