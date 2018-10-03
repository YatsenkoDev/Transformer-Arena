package com.aequilibrium.assignment.transfarena;

import android.os.SystemClock;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.aequilibrium.assignment.transfarena.gallery.ui.activity.GalleryActivity;
import com.aequilibrium.assignment.transfarena.preview.ui.PreviewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TransformerCreationTest {

    @Rule
    public IntentsTestRule<GalleryActivity> intentsTestRule = new IntentsTestRule<>(GalleryActivity.class);

    @Test
    public void testTransformerCreation() {
        onView(withId(R.id.add_button)).perform(click());
        intended(hasComponent(PreviewActivity.class.getName()));
        String transformerName = "Transformer " + Calendar.getInstance().getTimeInMillis();
        onView(withId(R.id.name_input)).perform(typeText(transformerName));
        onView(withId(R.id.save)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.pager)).perform(swipeUp());
        onView(withText(transformerName)).check(matches(isDisplayed()));
    }
}
