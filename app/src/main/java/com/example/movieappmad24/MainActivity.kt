package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.Purple40
import com.example.movieappmad24.ui.theme.Purple80
import com.example.movieappmad24.ui.theme.PurpleGrey40
import com.example.movieappmad24.ui.theme.PurpleGrey80

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomAppBar() },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            MovieList(movies = getMovies())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text("Movie App") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Purple80, // Set the background color
            titleContentColor = Purple40 // Set the title text color here
        )
    )
}

@Composable
fun BottomAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(PurpleGrey80), // Sets background color
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Home icon with text
        Column(
            modifier = Modifier
                .clickable { /* insert ClickButton EventHandler */ }
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
            Text("Home", fontSize = 14.sp)
        }

        // Watchlist icon with text
        Column(
            modifier = Modifier
                .clickable { /* insert ClickButton EventHandler */ }
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Filled.Star, contentDescription = "Watchlist")
            Text("Watchlist", fontSize = 14.sp)
        }
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie)
        }
    }
}

@Composable
fun MovieRow(movie: Movie){
    var showDetails by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movie_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = "placeholder image")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ){
                    Icon(
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to favorites")
                }
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
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    MovieAppMAD24Theme {
        MainScreen()
    }
}
