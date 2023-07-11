package com.kfouri.yapetest.ui.recipeList

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kfouri.yapetest.R
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.ui.common.TopBar
import com.kfouri.yapetest.ui.theme.Yape
import com.kfouri.yapetest.util.ConstantTest.LIST_NAME_TAG
import com.kfouri.yapetest.util.ConstantTest.LOADING_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_LIST_ITEM_TAG
import com.kfouri.yapetest.util.ConstantTest.RECIPE_LIST_TAG
import com.kfouri.yapetest.util.ConstantTest.SEARCHVIEW_TAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),
    navController: NavController
) {

    val uiState by viewModel.uiState.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, stringResource(R.string.app_name)) }
    ) { padding ->

        uiState.success?.let { recipeList ->
            Column(modifier = Modifier.padding(padding)) {
                SearchView(textState)
                RecipeList(
                    ArrayList(recipeList),
                    navController = navController,
                    state = textState,
                    viewModel
                )
            }
        }

        if (uiState.loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().testTag(LOADING_TAG)
            ) {
                CircularProgressIndicator()
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth().testTag(SEARCHVIEW_TAG),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun RecipeListItem(recipe: Recipe, onItemClick: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .testTag(RECIPE_LIST_ITEM_TAG)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable {
                recipe.id?.let { onItemClick(it) }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, colorResource(R.color.yape))
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Yape)
                        .testTag(LIST_NAME_TAG)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp, start = 8.dp, bottom = 4.dp),
                        text = it,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}

@Composable
fun RecipeList(
    list: ArrayList<Recipe>,
    navController: NavController,
    state: MutableState<TextFieldValue>,
    viewModel: RecipeListViewModel
) {
    var filteredRecipes: ArrayList<Recipe>

    LazyColumn(modifier = Modifier.fillMaxWidth().testTag(RECIPE_LIST_TAG)) {
        val searchedText = state.value.text

        filteredRecipes = viewModel.filterList(searchedText, list)

        items(filteredRecipes) { filteredRecipe ->
            RecipeListItem(
                recipe = filteredRecipe,
                onItemClick = { recipeId ->
                    viewModel.navigateToDetail(
                        navController,
                        recipeId
                    )
                }
            )
        }
    }
}