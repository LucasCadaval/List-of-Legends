import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import br.uri.listoflegends.viewModels.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        viewModel = MainViewModel()
        resources = ApplicationProvider.getApplicationContext<Context>().resources
    }

    @Test
    fun testSetLocale() {
        val languageCode = "en"
        val countryCode = "US"
        val configuration = resources.configuration

        viewModel.setLocale(languageCode, countryCode, resources)

        val expectedLocale = Locale(languageCode, countryCode)
        assertEquals(expectedLocale, Locale.getDefault())
        assertEquals(expectedLocale, configuration.locale)
    }
}
