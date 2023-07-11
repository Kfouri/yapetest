package com.kfouri.yapetest.ui.recipeDetail.model

import com.kfouri.yapetest.domain.model.Recipe

data class RecipeDetailUIState(
    val loading: Boolean = false,
    val success: Recipe? = null,
    val error: String? = null
)