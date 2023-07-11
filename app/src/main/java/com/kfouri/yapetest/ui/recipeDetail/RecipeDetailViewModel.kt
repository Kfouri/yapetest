package com.kfouri.yapetest.ui.recipeDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.GetRecipeDetailUseCase
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.ui.main.model.MenuOptions
import com.kfouri.yapetest.ui.recipeDetail.model.RecipeDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(RecipeDetailUIState())
        private set

    init {
        getRecipe()
    }

    private fun getRecipe() {
        savedStateHandle.get<Int>("id")?.let { id ->
            viewModelScope.launch {
                showLoading()
                when (val response = getRecipeDetailUseCase.invoke(id)) {
                    is Resource.Success -> {
                        setSuccessful(response.data)
                    }
                    is Resource.Error -> {
                        showError(response.message)
                    }
                }
            }
        }
    }

    private fun showError(message: String?) {
        state = state.copy(
            loading = false,
            error = message
        )
    }

    private fun showLoading() {
        state = state.copy(
            loading = true
        )
    }

    private fun setSuccessful(recipe: Recipe?) {
        state = state.copy(
            loading = false,
            success = recipe
        )
    }

    fun navigateToMap(navController: NavController, lat: Double, lon: Double, name: String) {
        navController.navigate(MenuOptions.Map.passData(lat, lon, name))
    }
}
