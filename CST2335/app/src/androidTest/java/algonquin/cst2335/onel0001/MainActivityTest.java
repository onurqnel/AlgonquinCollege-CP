package algonquin.cst2335.onel0001;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Find the EditText by its ID and perform a click on it
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.editText), isDisplayed()));
        appCompatEditText.perform(click());

        // Replace the text in the EditText with "12345"
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.editText), isDisplayed()));
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        // Find the button by its ID and perform a click on it
        ViewInteraction materialButton = onView(allOf(withId(R.id.button), isDisplayed()));
        materialButton.perform(click());

        // Verify that the TextView displays the correct text after the button is clicked
        ViewInteraction textView = onView(allOf(withId(R.id.textView), isDisplayed()));
        textView.check(matches(withText("You shall not pass!")));
    }


    /**
     * Test for validating password with missing uppercase character.
     * Password "password123*" is used as the test input.
     * The system should indicate that the password does not meet the requirements.
     */
    @Test
    public void testFindMissingUpperCase() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("password123*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test for validating password with missing digit.
     * Password "Password*" is used as the test input.
     * The system should indicate that the password does not meet the requirements.
     */
    @Test
    public void testFindMissingDigit() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("Password*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test for validating password with missing lowercase character.
     * Password "P123*" is used as the test input.
     * The system should indicate that the password does not meet the requirements.
     */
    @Test
    public void testFindMissingLowerCase() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("P123*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test for validating password with missing special character.
     * Password "Password123" is used as the test input.
     * The system should indicate that the password does not meet the requirements.
     */
    @Test
    public void testFindMissingSpecial() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("Password123"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test for validating password with all required elements.
     * Password "Password123*" is used as the test input.
     * The system should indicate that the password meets the requirements.
     */
    @Test
    public void testFindNoErrors() {
        //find the view
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        //type in password123#$*
        appCompatEditText.perform(replaceText("Password123*"));
        //find the button
        ViewInteraction materialButton = onView(withId(R.id.button));
        //click the button
        materialButton.perform(click());
        //find the text view
        ViewInteraction textView = onView(withId(R.id.textView));
        //check the text
        textView.check(matches(withText("Your password meets the requirements")));
    }


}
