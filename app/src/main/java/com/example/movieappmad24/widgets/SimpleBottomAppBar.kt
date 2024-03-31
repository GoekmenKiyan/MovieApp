package com.example.movieappmad24.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.Pink80
import com.example.movieappmad24.ui.theme.Purple40
import com.example.movieappmad24.ui.theme.Purple80
import com.example.movieappmad24.ui.theme.PurpleGrey40
import com.example.movieappmad24.ui.theme.PurpleGrey80

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = PurpleGrey80
    ) {
        NavigationBarItem(
            label = { Text("Home") },
            selected = currentRoute == Screen.HomeScreen.route,
            onClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = PurpleGrey40
            ),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Go to home"
                )
            }
        )
        NavigationBarItem(
            label = { Text("Watchlist") },
            selected = currentRoute == Screen.WatchlistScreen.route,
            onClick = {
                navController.navigate(Screen.WatchlistScreen.route) {
                    popUpTo(navController.graph.startDestinationId)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Go to watchlist"
                )
            }
        )
    }
}