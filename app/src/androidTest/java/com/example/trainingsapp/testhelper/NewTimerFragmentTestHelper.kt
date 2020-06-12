package com.example.trainingsapp.testhelper

import android.content.Context
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.trainingsapp.R
import org.junit.Assert

object NewTimerFragmentTestHelper {

    // Todo fix not found view (try restarting device?!)

    /** Resource Id's: */
    // new_timer_fragment layout:
    private const val TABLAYOUT_ID = "newTimerTabLayout"
    private const val VIEWPAGER_ID = "viewPager"
    private const val ADD_TIMER_BUTTON_ID = "addTimerButton"
    private const val CANCEL_TIMER_BUTTON_ID = "cancelTimerButton"

    // timer_layout:
    private const val MINUTES_PICKER_ID = "numpickerMinutes"
    private const val SECONDS_PICKER_ID = "numpickerSeconds"

    // new_constant_pattern layout:
    private const val EXPLANATION_TEXTVIEW_ID = "constantPatternExplanation"
    private const val STEPS_COUNT_TEXTVIEW_ID = "stepsCountText"
    private const val STEP_PICKER_ID = "stepPicker"
    private const val STEP_DURATION_TEXTVIEW_ID = "stepsDurationText"
    private const val STEP_TIMER_LAYOUT_ID = "stepTimerLayout"
    private const val BREAK_DURATION_TEXTVIEW_ID = "breakDurationText"
    private const val BREAK_TIMER_LAYOUT_ID = "breakTimerLayout"

    private lateinit var device: UiDevice
    private lateinit var context: Context

    fun createConstantTimer(device: UiDevice, context: Context) {
        this.device = device
        this.context = context
        checkNewTimerFragmentLayout()
        testConstantTimerLayout()
//        addConstantTimer()
    }

    private fun addConstantTimer() {
        device.findObject(
            UiSelector().resourceId(
                STEP_PICKER_ID
            )
        ).swipeUp(5)
        device.findObject(
            UiSelector().resourceId(
                STEP_TIMER_LAYOUT_ID
            ).childSelector(
                UiSelector().resourceId(
                    MINUTES_PICKER_ID
                )
            )
        ).swipeUp(53)
        device.findObject(
            UiSelector().resourceId(
                STEP_TIMER_LAYOUT_ID
            ).childSelector(
                UiSelector().resourceId(
                    SECONDS_PICKER_ID
                )
            )
        ).swipeUp(24)
        device.findObject(
            UiSelector().resourceId(
                BREAK_TIMER_LAYOUT_ID
            ).childSelector(
                UiSelector().resourceId(
                    MINUTES_PICKER_ID
                )
            )
        ).swipeUp(3)
        device.findObject(
            UiSelector().resourceId(
                BREAK_TIMER_LAYOUT_ID
            ).childSelector(
                UiSelector().resourceId(
                    SECONDS_PICKER_ID
                )
            )
        ).swipeUp(24)
        device.findObject(
            By.res(
                Utility.BASIC_SAMPLE_PACKAGE,
                ADD_TIMER_BUTTON_ID
            )
        ).click()
    }

    private fun testConstantTimerLayout() {
        Assert.assertTrue(
            device.findObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    EXPLANATION_TEXTVIEW_ID
                )
            ).text == context.getString(R.string.constant_pattern_explanation)
        )
        Assert.assertTrue(
            device.findObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    STEPS_COUNT_TEXTVIEW_ID
                )
            ).text == context.getString(R.string.step_count_text)
        )
//        Assert.assertTrue(
//            device.hasObject(
//                By.res(
//                    Utility.BASIC_SAMPLE_PACKAGE,
//                    STEP_PICKER_ID
//                )
//            )
//        )
        Assert.assertTrue(
            device.findObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    STEP_DURATION_TEXTVIEW_ID
                )
            ).text == context.getString(R.string.step_time_text)
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    STEP_TIMER_LAYOUT_ID
                )
            )
        )
        Assert.assertTrue(
            device.findObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    BREAK_DURATION_TEXTVIEW_ID
                )
            ).text == context.getString(R.string.break_time_text)
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    ADD_TIMER_BUTTON_ID
                )
            )
        )
    }

    private fun checkNewTimerFragmentLayout() {
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    TABLAYOUT_ID
                )
            )
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    VIEWPAGER_ID
                )
            )
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    ADD_TIMER_BUTTON_ID
                )
            )
        )
        Assert.assertTrue(
            device.hasObject(
                By.res(
                    Utility.BASIC_SAMPLE_PACKAGE,
                    BREAK_TIMER_LAYOUT_ID
                )
            )
        )
    }
}
