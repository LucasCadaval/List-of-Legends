package br.uri.listoflegends

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.uri.listoflegends.services.SharedPreferencesManager
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SharedPreferencesManagerTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    @Mock
    private lateinit var sharedPreferences: SharedPreferences
    @Mock
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        `when`(context.getSharedPreferences(SharedPreferencesManager.PREF_NAME, Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putInt(anyString(), anyInt())).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.remove(anyString())).thenReturn(editor)
        `when`(editor.apply()).thenAnswer {}
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSavePageIndex() {
        val pageIndex = 2
        SharedPreferencesManager.savePageIndex(context, pageIndex)

        verify(editor).putInt(SharedPreferencesManager.PAGE_INDEX, pageIndex)
        verify(editor).apply()
    }

    @Test
    fun testGetPageIndex() {
        val expectedPageIndex = 2
        `when`(sharedPreferences.getInt(SharedPreferencesManager.PAGE_INDEX, 1)).thenReturn(expectedPageIndex)

        val actualPageIndex = SharedPreferencesManager.getPageIndex(context)
        assertEquals(expectedPageIndex, actualPageIndex)
    }

    @Test
    fun testSaveChampions() {
        val championsJson = "{\"name\": \"Champion1\", \"title\": \"The Great\"}"
        SharedPreferencesManager.saveChampions(context, championsJson)

        verify(editor).putString(SharedPreferencesManager.CHAMPIONS, championsJson)
        verify(editor).apply()
    }

    @Test
    fun testGetChampions() {
        val expectedChampionsJson = "{\"name\": \"Champion1\", \"title\": \"The Great\"}"
        `when`(sharedPreferences.getString(SharedPreferencesManager.CHAMPIONS, null)).thenReturn(expectedChampionsJson)

        val actualChampionsJson = SharedPreferencesManager.getChampions(context)
        assertEquals(expectedChampionsJson, actualChampionsJson)
    }

    @Test
    fun testSaveItems() {
        val itemsJson = "[{\"name\": \"Item1\"}, {\"name\": \"Item2\"}]"
        SharedPreferencesManager.saveItems(context, itemsJson)

        verify(editor).putString(SharedPreferencesManager.ITEMS, itemsJson)
        verify(editor).apply()
    }

    @Test
    fun testGetItems() {
        val expectedItemsJson = "[{\"name\": \"Item1\"}, {\"name\": \"Item2\"}]"
        `when`(sharedPreferences.getString(SharedPreferencesManager.ITEMS, null)).thenReturn(expectedItemsJson)

        val actualItemsJson = SharedPreferencesManager.getItems(context)
        assertEquals(expectedItemsJson, actualItemsJson)
    }

    @Test
    fun testClearChampions() {
        SharedPreferencesManager.clearChampions(context)

        verify(editor).remove(SharedPreferencesManager.CHAMPIONS)
        verify(editor).apply()
    }

    @Test
    fun testClearItems() {
        SharedPreferencesManager.clearItems(context)

        verify(editor).remove(SharedPreferencesManager.ITEMS)
        verify(editor).apply()
    }

    @Test
    fun testClearPageIndex() {
        SharedPreferencesManager.clearPageIndex(context)

        verify(editor).remove(SharedPreferencesManager.PAGE_INDEX)
        verify(editor).apply()
    }
}
