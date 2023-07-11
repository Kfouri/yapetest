package com.kfouri.yapetest

import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.GetRecipesUseCase
import com.kfouri.yapetest.domain.model.Origen
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.model.RecipesModel
import com.kfouri.yapetest.domain.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {


    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @RelaxedMockK
    private lateinit var repository: RecipeRepository

    private val recipe = Recipe(
        id = 1,
        name = "nombre de prueba",
        image = "https//image.png",
        description = "descripcion de prueba",
        ingredients = "ingredientes de prueba",
        Origen(0.0, 0.0)
    )

    private val recipeModel = RecipesModel(
        listOf(
            recipe
        )
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRecipesUseCase = GetRecipesUseCase(repository)
    }

    @Test
    fun `Get Recipes, check Success`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipes()
        } returns Resource.Success(recipeModel)

        //When
        getRecipesUseCase()

        //Then
        coVerify(exactly = 1) {
             repository.getRecipes()
        }
    }

    @Test
    fun `Get Recipes with error, check Error`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipes()
        } returns Resource.Error("error")

        //When
        val response = getRecipesUseCase().message

        //Then
        coVerify(exactly = 1) {
            repository.getRecipes()
        }
        assert(response == "error")
    }

    @Test
    fun `Get Recipes, check list size`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipes()
        } returns Resource.Success(recipeModel)

        //When
        val response = getRecipesUseCase().data?.recipes

        //Then
        coVerify(exactly = 1) {
            repository.getRecipes()
        }
        assert(response?.size == 1)
    }
}