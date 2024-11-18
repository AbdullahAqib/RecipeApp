package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                val navigationController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(navigationController)
                }
            }
        }
    }
}

@Composable
fun MyApp(navigationController: NavHostController) {

    val mainViewModel : MainViewModel = viewModel()
    val categoriesState by mainViewModel.categoriesState

    NavHost(navController = navigationController, startDestination = Screen.RecipeScreen.route) {
        composable(Screen.RecipeScreen.route) {
            RecipeScreen(categoriesState = categoriesState, navigateToCategoryDetail = {
                navigationController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                navigationController.navigate(Screen.CategoryDetailScreen.route)
            })
        }
        composable(Screen.CategoryDetailScreen.route) {
            val category = navigationController.previousBackStackEntry?.savedStateHandle?.
                get<Category>("category") ?: Category("", "", "", "")
            CategoryDetailScreen(category = category)
        }
    }
}