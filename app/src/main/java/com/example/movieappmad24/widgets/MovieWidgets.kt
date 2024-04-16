package com.example.movieappmad24.widgets

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.Pink80
import com.example.movieappmad24.ui.theme.PurpleGrey80
import com.example.movieappmad24.viewmodels.MoviesViewModel


@Composable
fun MovieList(
    modifier: Modifier,
    viewModel: MoviesViewModel,
    navController: NavController,
    movies: List<Movie> = viewModel.movies
){

    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onFavoriteClick = {movieId ->
                    viewModel.toggleFavoriteMovie(movieId)
                },
                onItemClick = { movieId ->
                    navController.navigate(route = Screen.DetailScreen.withId(movieId))
                }
            )
        }
    }
}

@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    movie: Movie,
    onFavoriteClick: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {}
){
    // Card to display Movie-Rows
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            onItemClick(movie.id)
        },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp),
        // Change the Color of the Movie Card
        colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
    ) {
        // arrange vertically
        Column {

            MovieCardHeader(
                imageUrl = movie.images[0],
                isFavorite = movie.isFavorite,
                onFavoriteClick = { onFavoriteClick(movie.id) }
            )

            // Detail Section
            MovieDetails(modifier = modifier.padding(12.dp), movie = movie)

        }
    }
}

@Composable
fun MovieCardHeader(
    imageUrl: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        MovieImage(imageUrl)

        FavoriteIcon(isFavorite = isFavorite, onFavoriteClick)
    }
}

@Composable
fun MovieImage(imageUrl: String){
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "movie poster",
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(
            modifier = Modifier.clickable {
                onFavoriteClick() },
            tint = Color.Red,
            imageVector =
            if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },

            contentDescription = "Add to Watchlist")
    }
}


@Composable
fun MovieDetails(modifier: Modifier, movie: Movie) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title)
        Icon(modifier = Modifier
            .clickable {
                showDetails = !showDetails
            },
            imageVector =
            if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more")
    }


    AnimatedVisibility(
        visible = showDetails,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
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
fun MovieCardHeader(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            // Card to show first Image of Movie
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp), // Rounded Corners
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Shadows Card/Image
            ) {
                // Loading and Displaying Images
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) // Creates corners for the image
                )
            }
        }
    }
}

@Composable
fun MovieTrailer(movieTrailer: String){
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    val mediaItem =
        MediaItem.fromUri(
            "android.resource://${context.packageName}/$movieTrailer"
        )

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var wasPlayingBeforePause by remember {
        mutableStateOf(false)
    }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                    // Check if video was playing && if its not playing right now
                    if(wasPlayingBeforePause && exoPlayer.isPlaying.not()) { it.player?.playWhenReady = true }
                }

                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    // Check if video was playing after pressing pause
                    wasPlayingBeforePause = exoPlayer.isPlaying
                    // Pause the Trailer
                    it.player?.pause()
                }
                else -> Unit
            }
        }
    )
}