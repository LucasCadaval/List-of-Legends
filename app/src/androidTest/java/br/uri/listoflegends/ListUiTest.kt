package br.uri.listoflegends

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.Sprite
import br.uri.listoflegends.models.Stats
import br.uri.listoflegends.ui.ChampionList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionListUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchAndClickOnAatrox() {
        composeTestRule.setContent {
            ChampionList(
                onTeamDraftClick = {},
                onChampionClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Search...")
            .assertIsDisplayed()
            .performClick()

//        composeTestRule
//            .onNodeWithText("Search...")
//            .performTextInput("Aatrox")

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText("Aatrox")
            .assertIsDisplayed()
            .performClick()
    }
}