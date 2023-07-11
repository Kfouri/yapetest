package com.kfouri.yapetest.domain.model

data class RecipesModel(
    val recipes: List<Recipe>
)

data class Recipe(
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?,
    val ingredients: String?,
    val origen: Origen?
)

data class Origen (
    val lat: Double = 0.0,
    val lon: Double = 0.0
)
