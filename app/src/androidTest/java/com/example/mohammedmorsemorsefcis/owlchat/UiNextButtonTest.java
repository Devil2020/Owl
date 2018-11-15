package com.example.mohammedmorsemorsefcis.owlchat;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.mohammedmorsemorsefcis.owlchat.WelcomeActivityPackage.WelcomeActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UiNextButtonTest {
    @Rule
    public ActivityTestRule<WelcomeActivity> rule=new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class);
    @Test
   public void PressOnNext() throws InterruptedException {
        Thread.sleep(50000);
        Espresso.onView(ViewMatchers.withId(R.id.Next)).perform(ViewActions.longClick());

        Espresso.onView(ViewMatchers.withId(R.id.Before)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
