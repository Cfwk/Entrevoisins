
package com.openclassrooms.entrevoisins.neighbour_list;

import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoriteNeighbourFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ProfileNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import java.lang.NullPointerException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertTrue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private NeighbourApiService mNeighbourApiService;
    private List<Neighbour>favoriteNeighbours;
    private List<Neighbour>Neighbours;
    private int i;

    private ListNeighbourActivity mActivity;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule = new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mNeighbourApiService = DI.getNeighbourApiService();
        Neighbours = mNeighbourApiService.getNeighbours();
        mNeighbourApiService.favoriteNeighbour(Neighbours.get(0));
        mNeighbourApiService.favoriteNeighbour(Neighbours.get(1));
        favoriteNeighbours = mNeighbourApiService.getFavoriteNeighbours();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }


    /**
     *  We ensure that our recyclerview of favorite tab only display favorite items
     */
    @Test
    public void myFavoriteNeighboursList_shouldOnlyHaveFavoriteNeighbours() {

        // First scroll to the position that needs to be matched and click on it.
        i = 2;
        onView(withText("Favorites")).perform(click());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(i));

    }



    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }


    @Test
    public void ListNeighbourItemName_shouldStartAProfileNeighbourActivity() {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(withId(R.id.nameHeader), isDisplayed()))
                .check(matches(isDisplayed()));


    }

    @Test
    public void ProfileNeighbourActivity_shouldShowTheNeighbourNameOfNeighbourHeaderName() {

        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(withId(R.id.nameHeader), isDisplayed()))
                .check(matches(withText(Neighbours.get(1).getName())));

    }
}
