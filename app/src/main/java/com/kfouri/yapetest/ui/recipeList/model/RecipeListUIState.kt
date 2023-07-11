package com.kfouri.yapetest.ui.recipeList.model

import com.kfouri.yapetest.domain.model.Recipe

data class RecipeListUIState(
    val loading: Boolean = false,
    val success: List<Recipe>? = emptyList(),
    val error: String? = null
)