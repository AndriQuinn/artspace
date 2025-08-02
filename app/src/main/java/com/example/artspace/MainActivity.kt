package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import com.example.artspace.ui.screen.ArtSpaceHomeScreen
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.artspace.ui.screen.ArtSpaceAddContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // Set the navigation
    NavHost(navController = navController,startDestination = "home") {
        composable("home") { ArtSpaceHomeScreen(navController) }
        composable("postImage") { ArtSpaceAddContent(navController) }
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true,
    name = "Home Screen Window"
)
@Composable
fun HomeScreenPreview() {
    ArtSpaceTheme {
        val navController = rememberNavController()
        ArtSpaceHomeScreen(navController)
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true,
    name = "Add image window"
)
@Composable
fun AddImagePreview() {
    ArtSpaceTheme {
        val navController = rememberNavController()
        ArtSpaceAddContent(navController)
    }
}