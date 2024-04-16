package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel
){
    // Scaffold -> Sets up app bar structure for the Watchlist-Section
    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ){ innerPadding ->
        // Movies will be displayed here
        MovieList(
            modifier = Modifier.padding(innerPadding),
            viewModel = moviesViewModel,
            navController = navController,
            movies = moviesViewModel.favoriteMovies
        )

    }
}