package com.example.chaquiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chaquiapp.ui.screens.IncidentListScreen
import com.example.chaquiapp.ui.screens.NewIncidentScreen
import com.example.chaquiapp.ui.screens.IncidentDetailScreen
import com.example.chaquiapp.viewmodel.IncidentViewModel

sealed class Screen(val route: String) {
    object List : Screen("list")
    object New : Screen("new")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}

@Composable
fun AppNavigation(viewModel: IncidentViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            IncidentListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(Screen.New.route) {
            NewIncidentScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            IncidentDetailScreen(
                incidentId = id,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}