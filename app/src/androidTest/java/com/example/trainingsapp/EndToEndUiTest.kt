package com.example.trainingsapp

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.example.trainingsapp.testhelper.NewTimerFragmentTestHelper
import com.example.trainingsapp.testhelper.Utility
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EndToEndUiTest {

    private lateinit var device: UiDevice
    private lateinit var context: Context

    @Before
    fun startMainActivityFromHomeScreen() {
        context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        device = Utility.startApp(context)
    }

    @Test
    fun endToEndUiTest() {
        checkMainActivityLayout()
        startNewTimerFragment()
        NewTimerFragmentTestHelper.createConstantTimer(device, context)
    }

    private fun checkMainActivityLayout() {
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    CREATE_NEW_TIMER_BUTTON_ID
                )
            )
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    START_TRAINING_BUTTON_ID
                )
            )
        )
    }

    private fun startNewTimerFragment() {
        device.findObject(
            By.res(
                Utility.BASIC_SAMPLE_PACKAGE,
                CREATE_NEW_TIMER_BUTTON_ID
            )
        ).click()
    }

//    fun startApp(): UiDevice {
//        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//        val launcherPackage = device.launcherPackageName
//        Assert.assertNotNull(launcherPackage)
//        device.wait(
//            Until.hasObject(By.pkg(launcherPackage).depth(0)),
//            Utility.LAUNCH_TIMEOUT
//        )
//        val intent = context.packageManager.getLaunchIntentForPackage(
//            Utility.BASIC_SAMPLE_PACKAGE
//        )?.apply {
//            // Clear out any previous instances
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        }
//        context.startActivity(intent)
//
//        // Wait for the app to appear
//        device.wait(
//            Until.hasObject(By.pkg(Utility.BASIC_SAMPLE_PACKAGE).depth(0)),
//            Utility.LAUNCH_TIMEOUT
//        )
//        return device
//    }

    companion object {
        private const val CREATE_NEW_TIMER_BUTTON_ID = "createTimerButton"
        private const val START_TRAINING_BUTTON_ID = "startTrainingButton"
    }
}
