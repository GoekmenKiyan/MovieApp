package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.MovieCardHeader
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.MovieTrailer
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: String?,
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {

    movieId?.let {
        val movie = moviesViewModel.movies.filter { movie -> movie.id == movieId }[0]

        Scaffold (
            topBar = {
                SimpleTopAppBar(title = movie.title) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            }
        ){ innerPadding ->
            LazyColumn {
                item {
                    MovieRow(modifier = Modifier.padding(innerPadding),
                        movie = movie,
                        onFavoriteClick = {movieId ->
                            moviesViewModel.toggleFavoriteMovie(movieId)
                        })
                    MovieTrailer(movieTrailer = movie.trailer)
                    MovieCardHeader(movie = movie)
                }
            }
        }
    }
}
