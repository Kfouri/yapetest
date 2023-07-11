package com.kfouri.yapetest.di

import com.kfouri.yapetest.data.RecipeRepositoryImpl
import com.kfouri.yapetest.data.client.RecipeService
import com.kfouri.yapetest.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryTestModule {

    @Provides
    @Singleton
    fun providesRecipesRepository(
        recipeService: RecipeService
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService)
    }
}