package br.uri.listoflegends

import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import br.uri.listoflegends.R
import br.uri.listoflegends.services.sendNotification
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class NotificationServiceTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testSendNotification() {
        val channelId = "test_channel"
        val title = "Test Title"
        val descriptionText = "Test Description"
        val importance = NotificationManager.IMPORTANCE_HIGH

        sendNotification(channelId, title, descriptionText, context, importance)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val shadowNotificationManager = Shadows.shadowOf(notificationManager)

        val notification = shadowNotificationManager.getNotification(1)
        assert(notification != null)
        assert(notification!!.channelId == channelId)
        assert(notification.extras.getString("android.title") == title)
        assert(notification.extras.getString("android.text") == descriptionText)
    }
}