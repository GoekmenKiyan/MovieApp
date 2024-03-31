package com.example.movieappmad24.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.Purple40
import com.example.movieappmad24.ui.theme.PurpleGrey80

@Composable
fun MovieDetails(movie: Movie, isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        Column(modifier = Modifier
            .padding(8.dp)
            .background(PurpleGrey80) // Background Color of the Details Text
        ) {
            Text("Director: ${movie.director}", style = MaterialTheme.typography.bodyMedium)
            Text("Released: ${movie.year}", style = MaterialTheme.typography.bodyMedium)
            Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
            Text("Actors: ${movie.actors}", style = MaterialTheme.typography.bodyMedium)
            Text("Rating: ${movie.rating}", style = MaterialTheme.typography.bodyMedium)
            // Divider for better User Experience/Readability
            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )
            Text("Plot: ${movie.plot}", style = MaterialTheme.typography.bodyMedium)
            // Add more details if wanted/needed
        }
    }
}

@Composable
fun MovieRow(
    movie: Movie,
    onItemClick: (String) -> Unit,
    navController: NavController
) {
    var isFavorite by remember { mutableStateOf(false) }
    var showArrow by remember { mutableStateOf(false) }

    MovieAppMAD24Theme {
        Surface(modifier = Modifier
            .fillMaxWidth(),
            color = MaterialTheme.colorScheme.background)
        {
            Column {
                MovieCardHeader(movie, onItemClick, isFavorite, onFavoriteClick = { isFavorite = !isFavorite }, navController)
                MovieTitleBar(movie, showArrow, onArrowClick = { showArrow = !showArrow })
                MovieDetails(movie, showArrow)
            }
        }
    }
}


@Composable
fun MovieCardHeader(
    movie: Movie,
    onItemClick: (String) -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    navController: NavController
) {
    Box(modifier = Modifier
        .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    onItemClick(movie.id)
                    navController.navigate("detailscreen/${movie.id}")
                },
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = movie.images.firstOrNull() ?: "",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) // Creates corners for the image

            )
        }
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = { onFavoriteClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                tint = Color.Red,
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}


@Composable
fun MovieTitleBar(movie: Movie, showArrow: Boolean, onArrowClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            ) // Apply rounded corners to the bottom
            .background(PurpleGrey80) // Change background color of movie-box
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.title,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            color = Color.Black // Color of the Movie Name
        )
        IconButton(onClick = { onArrowClick() }) {
            Icon(
                imageVector = if (showArrow) Icons.Filled.KeyboardArrowUp else Icons.Default.KeyboardArrowUp,
                contentDescription = "Expand",
                modifier = Modifier.graphicsLayer(rotationZ = if (showArrow) 180f else 0f)
            )
        }
    }
}