package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.SimpleTopAppBar


@Composable
fun DetailScreen(navController: NavController, movieId: String) {
    val movie = getMovies().find { it.id == movieId }

    if (movie == null) {
        Text("Movie not found")
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopAppBar(navController = navController, title = movie.title, backButton = true)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                MovieRow(
                    movie = movie,
                    onItemClick = {},
                    navController = navController
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(start = 8.dp, top = 6.dp, bottom = 6.dp)
                ) {
                    items(movie.images) { image ->
                        MoviePicture(resourceLink = image, title = movie.title)
                    }
                }


            }
        }
    )

}


@Composable
fun MoviePicture(resourceLink: String, title: String) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        modifier = Modifier
            .padding(all = 15.dp)
    ) {
        AsyncImage(
            model = resourceLink,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_image),
            contentDescription = "$title Image",
            modifier = Modifier
                .aspectRatio(ratio = 1f)
        )
    }
}