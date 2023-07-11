package com.kfouri.yapetest.ui.main.model

sealed class MenuOptions(
    val route: String
) {
    object RecipeList : MenuOptions(
        route = "recipeList"
    )

    object RecipeDetail : MenuOptions(
        route = "recipeDetail?id={id}"
    ) {
        fun passId(id: Int): String {
            return "recipeDetail?id=$id"
        }
    }

    object Map : MenuOptions(
        route = "map?lat={lat}&lon={lon}&name={name}"
    ) {
        fun passData(lat: Double, lon: Double, name: String): String {
            return "map?lat=$lat&lon=$lon&name=$name"
        }
    }
}