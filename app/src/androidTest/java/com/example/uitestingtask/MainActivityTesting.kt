package com.example.uitestingtask

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Test
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.allOf

class MainActivityTesting {

    //define rule as all test cases in this class need instance of main activity
    //main activity get launches first then our test cases run
    //launcher activity
    @get:Rule
    val activityScenerioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testSubmitButton() {
        //on edit text of id nameEditText it will type text
        onView(withId(R.id.nameEditText)).perform(typeText("Hello"))

        //on edit text of id descriptionEditText it will type text and close soft keyboard
        onView(withId(R.id.descriptionEditText)).perform(
            typeText("I am description"),
            closeSoftKeyboard()
        )

        //on button of id submit it will perform click action and as we have already implemented a logic
        //of moving to next activity through intent so it will move next

        onView(withId(R.id.submitButton)).perform(click())

        //now it will check if input name and description matches with the text on next screen which
        //we are passing from previous screen
        onView(withId(R.id.nameTextView)).check(matches(withText("Hello")))
        onView(withId(R.id.descriptionTextView)).check(matches(withText("I am description")))

        //share button chooser opening test
        Intents.init()
        val expectedResult=allOf(hasAction(Intent.ACTION_SEND))
        onView(withId(R.id.shareButton)).perform(click())
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            intended(expectedResult)
            Intents.release()
        }

    }
}