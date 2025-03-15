package com.example.secretimage.resentation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(activity: Activity) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "imageScreen") {
        composable("imageScreen") { CameraScreen(activity,navController) }
        composable("secondScreen") { ImageScreen(navController) }
    }
}

@Composable
fun ImageScreen(navController: NavHostController) {
    // Your ImageScreen content
    Column {
        Text("This is Image Screen")
        Button(onClick = { navController.navigate("secondScreen") }) {
            Text("Go to Second Screen")
        }
    }
}

@Composable
fun SecondScreen(navController: NavHostController) {
    Column {
        Text("This is Second Screen")
        Button(onClick = { navController.navigate("imageScreen") }) {
            Text("Go to Image Screen")
        }
    }
}
