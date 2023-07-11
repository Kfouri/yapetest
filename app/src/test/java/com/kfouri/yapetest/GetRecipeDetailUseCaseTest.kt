package com.kfouri.yapetest

import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.GetRecipeDetailUseCase
import com.kfouri.yapetest.domain.model.Origen
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecipeDetailUseCaseTest {

    private lateinit var getRecipeDetailUseCase: GetRecipeDetailUseCase

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

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRecipeDetailUseCase = GetRecipeDetailUseCase(repository)
    }

    @Test
    fun `Get Recipe, check Success`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipe(1)
        } returns Resource.Success(recipe)

        //When
        getRecipeDetailUseCase(1)

        //Then
        coVerify(exactly = 1) {
            repository.getRecipe(1)
        }
    }

    @Test
    fun `Get Recipe, check name Success`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipe(1)
        } returns Resource.Success(recipe)

        //When
        val result = getRecipeDetailUseCase(1).data

        //Then
        coVerify(exactly = 1) {
            repository.getRecipe(1)
        }
        assert(result == recipe)
        assert(result?.name == "nombre de prueba")
    }

    @Test
    fun `Get Recipes with error, check Error`() = runBlocking {

        //Given
        coEvery {
            repository.getRecipe(1)
        } returns Resource.Error("error")

        //When
        val response = getRecipeDetailUseCase(1).message

        //Then
        coVerify(exactly = 1) {
            repository.getRecipe(1)
        }
        assert(response == "error")
    }
}