package com.example.budgettrackerchallenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.budgettrackerchallenge.fakes.FakeBudgetViewModel
import com.example.budgettrackerchallenge.ui.BudgetTrackerScreen.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeViewModel = FakeBudgetViewModel()


    @Test
    fun bottomSheetOpensWhenIncomeFabClicked() {
        composeTestRule.setContent {
            HomeScreen(viewModel = fakeViewModel)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("AddTransactionSheet")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("IncomeFAB")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("AddTransactionSheet")
            .assertIsDisplayed()
    }


    @Test
    fun bottomSheetOpensWhenExpenseFabClicked() {
        composeTestRule.setContent {
            HomeScreen(viewModel = fakeViewModel)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("AddTransactionSheet")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("ExpenseFAB")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("AddTransactionSheet")
            .assertIsDisplayed()
    }
}