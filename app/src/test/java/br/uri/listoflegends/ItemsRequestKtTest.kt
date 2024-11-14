package br.uri.listoflegends

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import br.uri.listoflegends.models.ItemModel
import br.uri.listoflegends.services.fetchItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class FetchItemsTest {
    private lateinit var mockWebServer: MockWebServer

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        MockitoAnnotations.openMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start(3001)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchItemsReturnsItemsSuccessfully() = runTest {
        val mockJsonResponse = MockResponse().setBody("")
        mockWebServer.enqueue(mockJsonResponse)

        val items = mutableListOf<ItemModel>()
        var responseCode: Int? = null
        val latch = CountDownLatch(1)

        fetchItems(context) { code, result ->
            responseCode = code
            items.addAll(result!!)
            latch.countDown()
        }

        latch.await(5, TimeUnit.SECONDS)

        assertEquals(20, items.size)
        assertEquals(200, responseCode)
        assertEquals("Boots of Speed", items[0].name)
    }
}
