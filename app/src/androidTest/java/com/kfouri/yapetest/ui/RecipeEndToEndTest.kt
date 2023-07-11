package com.kfouri.yapetest.ui


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kfouri.yapetest.MainActivity
import com.kfouri.yapetest.domain.di.RepositoryModule
import com.kfouri.yapetest.ui.main.model.MenuOptions
import com.kfouri.yapetest.ui.map.MapScreen
import com.kfouri.yapetest.ui.recipeDetail.RecipeDetailScreen
import com.kfouri.yapetest.ui.recipeList.RecipeListScreen
import com.kfouri.yapetest.ui.recipeList.waitUntilDoesNotExist
import com.kfouri.yapetest.ui.recipeList.waitUntilExists
import com.kfouri.yapetest.ui.theme.YapeTestTheme
import com.kfouri.yapetest.util.ConstantTest.LOADING_TAG
import com.kfouri.yapetest.util.ConstantTest.MAP_SCREEN_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_DETAIL_MAP_BUTTON_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_DETAIL_NAME_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_DETAIL_TITLE_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_LIST_ITEM_TAG
import com.kfouri.yapetest.util.ConstantTest.SEARCHVIEW_TAG
import com.kfouri.yapetest.util.ConstantTest.SEARCHVIEW_TEXT_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class RecipeEndToEndTest {

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
                    composable(
                        route = MenuOptions.RecipeDetail.route,
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) {
                        RecipeDetailScreen(navController = navController)
                    }
                    composable(
                        MenuOptions.Map.route,
                        arguments = listOf(
                            navArgument("lat") { type = NavType.FloatType },
                            navArgument("lon") { type = NavType.FloatType },
                            navArgument("name") { type = NavType.StringType },
                        )
                    ) { backStackEntry ->
                        val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: run  { 0.0 }
                        val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: run  { 0.0 }
                        val name = backStackEntry.arguments?.getString("name")?: run { "" }
                        MapScreen(lat, lon, name)
                    }
                }
            }
        }
    }

    @Test
    fun filterRecipe_selectRecipe_pressButtonMap_checkIfEverythingIsWorkingAsSpected() {
        //Enter into Recipe List and filter by "papa"
        composeRule.onNodeWithTag(SEARCHVIEW_TAG).performClick().performTextInput(SEARCHVIEW_TEXT_TAG)
        //Wait until response finish
        composeRule.waitUntilDoesNotExist(hasTestTag(LOADING_TAG))
        //Get first result and click
        composeRule.onAllNodesWithTag(RECIPE_LIST_ITEM_TAG)[0].performClick()
        //Wait until new screen is shown
        composeRule.waitUntilExists(hasTestTag(RECIPE_DETAIL_NAME_TAG))
        //Check if recipe name is "Tortilla de papas"
        composeRule.onNodeWithTag(RECIPE_DETAIL_NAME_TAG).assertIsDisplayed().assertTextEquals(RECIPE_DETAIL_TITLE_TAG)
        composeRule.onNodeWithTag(RECIPE_DETAIL_MAP_BUTTON_TAG).assertExists().performClick()
        //Wait until new screen is shown
        composeRule.waitUntilExists(hasTestTag(MAP_SCREEN_TAG))
        //Check if map could be opened
        composeRule.onNodeWithTag(MAP_SCREEN_TAG).assertExists()
    }
}