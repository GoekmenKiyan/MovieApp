package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen


@Composable
fun Navigation(movies: List<Movie>) {
    val navController = rememberNavController()

    NavHost(
        // NavController -> to handle all navigation actions
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        // Defining the "Movie details" screen
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = "movieId") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let { id ->
                val movie = movies.find { it.id == id }
                movie?.let {
                    DetailScreen(navController = navController, movieId = id)
                }
            }
        }
        // Defining the "Watchlist" screen
        composable(Screen.WatchlistScreen.route)
        {
            WatchlistScreen( navController = navController)
        }
    }
}