package com.kfouri.yapetest.ui.recipeList

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kfouri.yapetest.MainActivity
import com.kfouri.yapetest.domain.di.RepositoryModule
import com.kfouri.yapetest.ui.main.model.MenuOptions
import com.kfouri.yapetest.ui.theme.YapeTestTheme
import com.kfouri.yapetest.util.ConstantTest.RECIPE_LIST_ITEM_TAG
import com.kfouri.yapetest.util.ConstantTest.SEARCHVIEW_TAG
import com.kfouri.yapetest.util.ConstantTest.SEARCHVIEW_TEXT_TAG
import com.kfouri.yapetest.util.ConstantTest.TOPAPPBAR_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class RecipeListScreenTest {

    @JvmField
    @Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @JvmField
    @Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()

        composeRule.setContent {
            val navController = rememberNavController()
            YapeTestTheme(darkTheme = false) {

                NavHost(navController = navController, startDestination = MenuOptions.RecipeList.route) {
                    composable(route = MenuOptions.RecipeList.route) {
                        RecipeListScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun filterRecipe_checkIsDisplayedFirstResult() {
        composeRule.onNodeWithTag(TOPAPPBAR_TAG).assertIsDisplayed()
        composeRule.onNodeWithTag(SEARCHVIEW_TAG).performClick().performTextInput(SEARCHVIEW_TEXT_TAG)
        composeRule.onAllNodes(hasTestTag(RECIPE_LIST_ITEM_TAG)).apply {
            fetchSemanticsNodes().forEachIndexed { i, _ ->
                if (i == 0) {
                    get(i).assertExists()
                }
            }
        }
    }
}

fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1000L
) = waitUntilNodeCount(matcher, 1, timeoutMillis)

fun ComposeContentTestRule.waitUntilDoesNotExist(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = 1000L
) = waitUntilNodeCount(matcher, 0, timeoutMillis)

fun ComposeContentTestRule.waitUntilNodeCount(
    matcher: SemanticsMatcher,
    count: Int,
    timeoutMillis: Long = 1000L
) {
    waitUntil(timeoutMillis) {
        onAllNodes(matcher).fetchSemanticsNodes().size == count
    }
}