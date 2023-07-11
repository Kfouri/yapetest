package com.kfouri.yapetest.ui.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.GetRecipesUseCase
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.ui.main.model.MenuOptions
import com.kfouri.yapetest.ui.recipeList.model.RecipeListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(RecipeListUIState())
    val uiState: StateFlow<RecipeListUIState> = _uiState.asStateFlow()

    init {
        getRecipes()
    }

    private fun getRecipes() {

        viewModelScope.launch {
            showLoading()
            when(val response = getRecipesUseCase.invoke()){
                is Resource.Success -> {
                    setSuccessful(response.data?.recipes)
                }
                is Resource.Error -> {
                    showError(response.message)
                }
            }
        }
    }

    private fun showError(message: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                error = message
            )
        }
    }

    private fun showLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                loading = true
            )
        }
    }

    private fun setSuccessful(list: List<Recipe>?) {
        _uiState.update { currentState ->
            currentState.copy(
                loading = false,
                success = list
            )
        }
    }

    fun filterList(searchedText: String, list: ArrayList<Recipe>): ArrayList<Recipe> {

        val filteredRecipes = if (searchedText.isEmpty()) {
            list
        } else {
            val resultList = ArrayList<Recipe>()
            for (recipe in list) {
                recipe.name?.let {
                    if (it.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(recipe)
                    }
                }
                recipe.ingredients?.let {
                    if (it.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        if (!resultList.contains(recipe)) {
                            resultList.add(recipe)
                        }
                    }
                }
            }
            resultList
        }

        return filteredRecipes
    }

    fun navigateToDetail(navController: NavController, recipeId: Int) {
        navController.navigate(MenuOptions.RecipeDetail.passId(recipeId)) {
            launchSingleTop = true
            restoreState = true
            popUpTo(MenuOptions.RecipeList.route) {
                saveState = true
            }
        }
    }
}