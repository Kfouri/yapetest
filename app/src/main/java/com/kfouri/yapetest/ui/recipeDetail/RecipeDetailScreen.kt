package com.kfouri.yapetest.ui.recipeDetail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kfouri.yapetest.R
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.ui.common.TopBar
import com.kfouri.yapetest.util.ConstantTest.RECIPE_DETAIL_MAP_BUTTON_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_DETAIL_NAME_TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.state

    uiState.success?.let { recipe ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(navController, recipe.name ?: "") }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                RecipeDetail(
                    recipe,
                    navController = navController,
                    viewModel
                )
            }
        }
    }

    if (uiState.loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    uiState.error?.let { message ->
        Toast.makeText(
            LocalContext.current,
            "Error: $message",
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
fun RecipeDetail(recipe: Recipe, navController: NavController, viewModel: RecipeDetailViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(recipe.image)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        recipe.name?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.testTag(RECIPE_DETAIL_NAME_TAG),
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        recipe.description?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            ) {

                Text(text = it)

            }
        }

        recipe.ingredients?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.ingredients),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(modifier = Modifier.padding(top = 8.dp), text = it, fontSize = 14.sp)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {

            Button(modifier = Modifier.fillMaxWidth().testTag(RECIPE_DETAIL_MAP_BUTTON_TAG),
                onClick = {
                    viewModel.navigateToMap(
                        navController,
                        recipe.origen?.lat ?: 0.0,
                        recipe.origen?.lon ?: 0.0,
                        recipe.name ?: ""
                    )

                }) {
                Text(text = stringResource(R.string.navigate))
            }
        }

    }

}