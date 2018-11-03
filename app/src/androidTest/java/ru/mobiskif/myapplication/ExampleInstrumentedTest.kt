package ru.mobiskif.myapplication

//import android.support.test.InstrumentationRegistry
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
//import android.support.test.runner.AndroidJUnit4

//import org.junit.Test
//import org.junit.runner.RunWith
//import junit.framework.Assert.assertEquals
import android.accessibilityservice.AccessibilityService
import android.content.Intent

//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(Activity1::class.java)

    @Before
    fun jumpToPlantDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                //val bundle = PlantDetailFragmentArgs.Builder(testPlant.plantId).build().toBundle()
                //findNavController(R.id.nav_layout).navigate(R.id.search_mag_icon, null)
            }
        }
    }

    @Test
    fun testShareTextIntent() {
        //val shareText = activityTestRule.activity.getString(R.string.share_text_plant, testPlant.name)

        Intents.init()
        onView(withId(R.id.search_mag_icon)).perform(click())
        intended(
                chooser(
                        CoreMatchers.allOf(
                                hasAction(Intent.ACTION_SEND),

                                hasType("text/plain"),
                                hasExtra(Intent.EXTRA_TEXT, "qweqwe")
                        )
                )
        )
        Intents.release()

        // dismiss the Share Dialog
        InstrumentationRegistry.getInstrumentation()
                .uiAutomation
                .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("ru.mobiskif.myapplication", appContext.packageName)
    }
}

fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = Matchers.allOf(
        hasAction(Intent.ACTION_CHOOSER),
        hasExtra(Matchers.`is`(Intent.EXTRA_INTENT), matcher)
)
