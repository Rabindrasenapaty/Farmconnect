package com.rabindra.farmconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rabindra.farmconnect.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "buyer"
            LoginScreen(navController, userType)
        }
        composable("signup/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "buyer"
            SignUpScreen(navController, userType)
        }
        composable("buyer_dashboard") {
            BuyerDashboard { option ->
                when (option) {
                    "browse_produce" -> navController.navigate("marketplace")
                    "post_requirements" -> navController.navigate("post_requirements")
                    "my_contracts" -> navController.navigate("my_contracts")
                    "notifications" -> navController.navigate("notifications")
                    "profile" -> navController.navigate("profile/buyer")
                }
            }
        }
        composable("farmer_dashboard") {
            FarmerDashboard { option ->
                when (option) {
                    "add_contract" -> navController.navigate("add_contract")
                    "my_contracts" -> navController.navigate("my_contracts")
                    "crop_analytics" -> navController.navigate("crop_analytics")
                    "notifications" -> navController.navigate("notifications")
                    "profile" -> navController.navigate("profile/farmer")
                }
            }
        }
        composable("add_contract") {
            AddCropScreen(navController)
        }
        composable("profile/{userType}") { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType")
            ProfileScreen(userType)
        }
        composable("marketplace") {
            MarketplaceScreen(navController)
        }

    }
}
