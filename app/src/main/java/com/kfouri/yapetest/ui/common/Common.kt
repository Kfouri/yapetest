@file:OptIn(ExperimentalMaterial3Api::class)

package com.kfouri.yapetest.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kfouri.yapetest.R
import com.kfouri.yapetest.util.ConstantTest.TOPAPPBAR_TAG

@Composable
fun TopBar(navController: NavController, title: String) {
    TopAppBar(
        modifier = Modifier.testTag(TOPAPPBAR_TAG),
        title = { Text(text = title, fontSize = 18.sp) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.yape),
            titleContentColor = Color.White
        ),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}