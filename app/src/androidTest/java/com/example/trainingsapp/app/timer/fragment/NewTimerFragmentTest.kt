package com.example.trainingsapp.app.timer.fragment

import android.content.Context
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.trainingsapp.R
import com.example.trainingsapp.app.main.MainActivity
import com.example.trainingsapp.app.timer.CustomNumberPicker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NewTimerFragmentTest {

    private var context: Context? = null

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        context = activityRule.activity.context
    }

    @Test
    fun testNewTimerFragmentStartUpToCancel() {
        startNewTimerFragment()
        testNewTimerFragmentLayout()
        testSwitchingFragments()
        testCancelButton()
    }

    @Test
    fun testNewTimerFragmentStartUpToCancelRepetition() {
        startNewTimerFragment()
        testNewTimerFragmentLayout()
        testSwitchingFragments()
        testCancelButton()
        startNewTimerFragment()
        testNewTimerFragmentLayout()
        testSwitchingFragments()
        testCancelButton()
    }

    @Test
    fun testNewTimerFragmentCreatingConstantTimer() {
        startNewTimerFragment()
        testCreatingConstantTimer()
    }

    @Test
    fun testNewTimerFragmentCreatingIndividualTimer() {
        startNewTimerFragment()
        testCreatingIndividualTimer()
    }

    private fun testCreatingIndividualTimer() {
        testCreateIndividualTimerFragmentLayoutStart()
        testFinishCreatingIndividualTimer()
    }

    private fun testFinishCreatingIndividualTimer() {
        testAddingIndividualTimerSteps()
        setIndividualTimerValues()
        addTimer()
    }

    private fun testAddingIndividualTimerSteps() {
        onView(withId(R.id.individualTimerRecyclerView))
            .check(matches(hasChildCount(0)))
        onView(withId(R.id.addTimerStepFAB))
            .perform(click())
        onView(withId(R.id.individualTimerRecyclerView))
            .check(matches(hasChildCount(1)))
        onView(withId(R.id.addTimerStepFAB))
            .perform(click())
        onView(withId(R.id.individualTimerRecyclerView))
            .check(matches(hasChildCount(2)))
    }

    private fun testCreatingConstantTimer() {
        testCreateConstantTimerFragmentLayoutStart()
        testFinishCreatingConstantTimer()
    }

    private fun testFinishCreatingConstantTimer() {
        setConstantTimerValues()
        addTimer()
    }

    private fun addTimer() {
        onView(withId(R.id.addTimerButton))
            .perform(click())
        onView(withId(R.id.createTimerButton))
            .check(matches(isDisplayed()))
    }

    private fun setConstantTimerValues() {
        val picker = activityRule.activity.findViewById<CustomNumberPicker>(R.id.stepPicker)
        val stepMinutesPicker =
            activityRule.activity.findViewById<LinearLayout>(R.id.stepTimerLayout)
                .findViewById<CustomNumberPicker>(R.id.numpickerMinutes)
        val stepSecondsPicker =
            activityRule.activity.findViewById<LinearLayout>(R.id.stepTimerLayout)
                .findViewById<CustomNumberPicker>(R.id.numpickerSeconds)
        val breakMinutesPicker =
            activityRule.activity.findViewById<LinearLayout>(R.id.breakTimerLayout)
                .findViewById<CustomNumberPicker>(R.id.numpickerMinutes)
        val breakSecondsPicker =
            activityRule.activity.findViewById<LinearLayout>(R.id.breakTimerLayout)
                .findViewById<CustomNumberPicker>(R.id.numpickerSeconds)
        picker.value = constantTimerCountValue
        stepMinutesPicker.value = constantTimerMinutesStepValue
        stepSecondsPicker.value = constantTimerSecondsStepValue
        breakMinutesPicker.value = constantTimerMinutesBreakValue
        breakSecondsPicker.value = constantTimerSecondsBreakValue
    }

    private fun setIndividualTimerValues() {
        activityRule.activity.findViewById<CustomNumberPicker>(R.id.numpickerMinutes).value =
            constantTimerMinutesBreakValue
        activityRule.activity.findViewById<CustomNumberPicker>(R.id.numpickerMinutes).value =
            constantTimerSecondsBreakValue
    }

    private fun startNewTimerFragment() {
        onView(withId(R.id.createTimerButton))
            .perform(click())
    }

    private fun testNewTimerFragmentLayout() {
        onView(withId(R.id.newTimerTabLayout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.viewPager))
            .check(matches(isDisplayed()))
        onView(withId(R.id.addTimerButton))
            .check(
                matches(
                    withText(
                        context?.getString(R.string.add_new_timer_button_text)
                    )
                )
            )
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
        onView(withId(R.id.cancelTimerButton))
            .check(
                matches(
                    withText(
                        context?.getString(R.string.cancel_new_timer_button_text)
                    )
                )
            )
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
    }

    private fun testSwitchingFragments() {
        testCreateIndividualTimerFragmentLayoutStart()
        testCreateConstantTimerFragmentLayoutStart()
        testCreateIndividualTimerFragmentLayoutStart()
    }

    private fun testCreateConstantTimerFragmentLayoutStart() {
        onView(withText(context?.getString(R.string.constant_pattern_tab_text)))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.constantPatternExplanation))
            .check(matches(isDisplayed()))
            .check(matches(withText(context?.getString(R.string.constant_pattern_explanation))))
        onView(withId(R.id.stepsCountText))
            .check(matches(isDisplayed()))
            .check(matches(withText(context?.getString(R.string.step_count_text))))
        onView(withId(R.id.stepPicker))
            .check(matches(isDisplayed()))
        onView(withId(R.id.stepsDurationText))
            .check(matches(isDisplayed()))
            .check(matches(withText(context?.getString(R.string.step_time_text))))
        onView(withId(R.id.stepTimerLayout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.breakDurationText))
            .check(matches(isDisplayed()))
            .check(matches(withText(context?.getString(R.string.break_time_text))))
        onView(withId(R.id.breakTimerLayout))
            .check(matches(isDisplayed()))
    }

    private fun testCreateIndividualTimerFragmentLayoutStart() {
        onView(withText(context?.getString(R.string.individual_pattern_tab_text)))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.individualTimerExplanation))
            .check(matches(isDisplayed()))
            .check(matches(withText(context?.getString(R.string.individual_pattern_explanation))))
        onView(withId(R.id.individualTimerRecyclerView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.addTimerStepFAB))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
    }

    private fun testCancelButton() {
        onView(withId(R.id.cancelTimerButton))
            .perform(click())
        onView(withId(R.id.createTimerButton))
            .check(matches(isDisplayed()))
    }

    companion object {
        private const val constantTimerCountValue = 3
        private const val constantTimerMinutesStepValue = 10
        private const val constantTimerSecondsStepValue = 57
        private const val constantTimerMinutesBreakValue = 0
        private const val constantTimerSecondsBreakValue = 30
    }
}
