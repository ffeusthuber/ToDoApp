package dev.ffeusthuber.todoapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NavigationTests {

    @Test
    public void test_nav_ActivityLogin_toActivityToDoList() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.layoutToDoList)).check(matches(isDisplayed()));
    }

    @Test
    public void test_nav_ActivityToDoList_toActivityNewTask() {
        ActivityScenario.launch(ToDoListActivity.class);
        onView(withId(R.id.btnNewTask)).perform(click());
        onView(withId(R.id.layoutNewTask)).check(matches(isDisplayed()));
    }

    @Test
    public void test_nav_ActivityNewTask_toActivityToDoList_viaBtnCancel() {
        ActivityScenario.launch(NewTaskActivity.class);
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.layoutToDoList)).check(matches(isDisplayed()));
    }

    @Test
    public void test_nav_ActivityNewTask_toActivityToDoList_viaBtnCreateTask() {
        ActivityScenario.launch(NewTaskActivity.class);
        onView(withId(R.id.btnCreateTask)).perform(click());
        onView(withId(R.id.layoutToDoList)).check(matches(isDisplayed()));
    }

    @Test
    public void test_nav_backpress_toActivityToDoList_fromActivityNewTask_viaBtnNewTask() {
        ActivityScenario.launch(ToDoListActivity.class);
        onView(withId(R.id.btnNewTask)).perform(click());
        pressBack();
        onView(withId(R.id.layoutToDoList)).check(matches(isDisplayed()));
    }

}