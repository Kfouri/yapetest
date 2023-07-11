package com.kfouri.yapetest.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kfouri.yapetest.ui.main.model.MenuOptions
import com.kfouri.yapetest.ui.map.MapScreen
import com.kfouri.yapetest.ui.recipeDetail.RecipeDetailScreen
import com.kfouri.yapetest.ui.recipeList.RecipeListScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MenuOptions.RecipeList.route
    ) {
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