package br.uri.listoflegends

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.ui.ChampionList
import br.uri.listoflegends.utils.Screen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchChampionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun searchAndClickOnAphelios() {

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
            .onNodeWithText(searchHint)
            .performClick()
            .performTextInput("aphelios")


        composeTestRule.waitForIdle()


        composeTestRule
            .onNodeWithText("Aphelios")
            .assertIsDisplayed()
            .performClick()

    }
}