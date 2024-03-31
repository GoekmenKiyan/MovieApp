package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(navController: NavController) {
    AppScaffold(navController = navController)
}


@Composable
fun AppScaffold(navController: NavController) {
    MovieAppMAD24Theme {
        Scaffold(
            topBar = {
                SimpleTopAppBar(navController = navController, title = "Movie App", backButton = false) },
            bottomBar = {
                SimpleBottomAppBar(navController) }
        ) { innerPadding ->
            MovieList(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun MovieList(navController: NavController, modifier: Modifier = Modifier) {
    val movies = getMovies()

    // Settings for displaying the movies with click-EventHandlers
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(movies) { movie ->
            com.example.movieappmad24.widgets.MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate("detailscreen/$movieId")
                },
                navController = navController
            )
        }
    }
}

// ----- TESTING AREA ----- //

// Preview for coding purposes
@Preview
@Composable
fun MovieAppPreview() {
    val navController = rememberNavController()
    AppScaffold(navController = navController)
}