package br.uri.listoflegends

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.uri.listoflegends.ui.ChampionList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionListUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()


    @Test
    fun searchAndClickOnAatrox() {

        val searchHint: String = context.getString(R.string.search_hint)

        composeTestRule.setContent {
            ChampionList(
                onTeamDraftClick = {},
                onChampionClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(searchHint)
            .assertIsDisplayed()
            .performClick()

        composeTestRule
            .onNodeWithText("Search...")
            .performClick()
            .performTextInput("aphelios")

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText("Aphelios")
            .assertIsDisplayed()
            .performClick()
    }
}