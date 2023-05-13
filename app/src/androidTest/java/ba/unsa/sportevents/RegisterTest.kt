package ba.unsa.sportevents

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class RegisterTest {


    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appLaunch(){
        // Launch the MainActivity
        activityScenarioRule.scenario
    }

    @Test
    fun navigateToRegister(){
        // Launch the MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Click on the Register button
        composeTestRule.onNodeWithText("Sign up with email").performClick()
    }

    @Test
    fun testButtonThrowsException() {
        // Launch the MainActivity
        activityScenarioRule.scenario

        // Perform the action of clicking the button
        var exceptionThrown = false
        try {
            composeTestRule.onNodeWithText("Sign up with email").performClick()
        } catch (e: Exception) {
            exceptionThrown = true
        }

        // Assert that an exception was not thrown
        assert(!exceptionThrown)
    }

    @Test
    fun emptyEmailField() {

        activityScenarioRule.scenario
        composeTestRule.onNodeWithText("Sign up with email").performClick()
        composeTestRule.onNodeWithText("Next").performClick()

        //Check if the node with text "Sign up" is present
        composeTestRule.onNodeWithText("Sign up").assertExists()
    }

    @Test
    fun googleLoginTrigger(){
        // Launch the MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Click on the Register button
        composeTestRule.onNodeWithText("Continue with Google").performClick()

        // Wait for the Google Sign In page to load
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.wait(Until.hasObject(By.text("Choose an account")), 10000)

        // Check if google accounts are visible
        val googleMenu = device.findObject(By.text("Choose an account"))
        Assert.assertTrue(googleMenu != null)

    }


}