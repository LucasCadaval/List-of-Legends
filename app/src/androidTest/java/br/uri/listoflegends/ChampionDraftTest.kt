package br.uri.listoflegends

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.ui.ChampionDraft
import br.uri.listoflegends.ui.ChampionList
import br.uri.listoflegends.utils.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionDraftTest {
        @get:Rule
        val composeTestRule = createComposeRule()

        private val context: Context = ApplicationProvider.getApplicationContext()

        @Test
        fun testChampionDraft() {
            val draftHint: String = context.getString(R.string.team_draft)

            composeTestRule.setContent {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.ChampionList) }

                when (currentScreen) {
                    is Screen.ChampionList -> ChampionList(
                        onTeamDraftClick = { currentScreen = Screen.ChampionDraft },
                        onChampionClick = {}
                    )
                    is Screen.ChampionDraft -> ChampionDraft()
                    else -> {}
                }
            }

            composeTestRule.waitUntil(3000){
                composeTestRule.onNodeWithText(draftHint).assertIsDisplayed().isDisplayed()
            }
            composeTestRule
                .onNodeWithText(context.getString(R.string.team_draft))
                .assertIsDisplayed()
                .performClick()
            Thread.sleep(1000)

            composeTestRule
                .onNodeWithText(context.getString(R.string.sort_teams))
                .assertIsDisplayed()
                .performClick()

            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithText(context.getString(R.string.blue_team))
                .assertIsDisplayed()
            composeTestRule.onNodeWithText(context.getString(R.string.red_team))
                .assertIsDisplayed()
        }
    }