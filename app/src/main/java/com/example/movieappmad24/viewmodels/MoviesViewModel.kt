package com.example.movieappmad24.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

// Inherit from ViewModel class
class MoviesViewModel : ViewModel() {

    // Getting all movies and converting to StateHolder -> to use in UI
    private val _movies = getMovies().toMutableStateList()
    val movies: List<Movie>
        get() = _movies

    val favoriteMovies: List<Movie>
        get() = _movies.filter { movie -> movie.isFavorite }

    fun toggleFavoriteMovie(movieId: String) = _movies.find { it.id == movieId }?.let { movie ->
        movie.isFavorite = !movie.isFavorite
    }

}