package com.rabindra.farmconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
            WelcomeScreen(navController, context = navController.context)
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
                    "buyer_contract_screen" -> navController.navigate("buyer_contract_screen")
                    "notifications" -> navController.navigate("notifications")
                    "profile" -> navController.navigate("profile/buyer")
                }
            }
        }
        composable("farmer_dashboard") {
            FarmerDashboard { option ->
                when (option) {
                    "add_contract" -> navController.navigate("add_contract")
                    "my_contracts" -> navController.navigate("my_contracts") // Navigate to Farmer Contracts Screen
                    "crop_analytics" -> navController.navigate("crop_analytics")
                    "notifications" -> navController.navigate("farmer_notifications")
                    "profile" -> navController.navigate("profile/farmer")
                    "buyer_contracts" -> navController.navigate("buyer_contracts")
                }
            }
        }
        composable("farmer_notifications") {
            FarmerNotificationsScreen(navController = navController)
        }
        composable("FaContractDetailsScreen/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: ""
            FaContractDetailsScreen(navController = navController, contractId = contractId)
        }


        composable("buyer_contracts") {
            FBuyerContractsScreen(navController = navController)
        }
        composable("chat_screen/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: ""
            ChatScreen(navController = navController, contractId = contractId)
        }
        composable("my_contracts") {
            FarmerContractsScreen(navController) // Farmer Contracts Screen
        }
        composable("contract_details/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: "N/A"
            FContractDetailsScreen(contractId = contractId, navController = navController) // Contract Details Screen for Farmer
        }
        composable("confirmation_screen/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: ""
            ConfirmationScreen(navController, contractId)
        }

        composable("agreement_form_screen/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: ""
            AgreementFormScreen(navController, contractId, context = LocalContext.current)
        }

        composable("crop_analytics") {
            CropAnalyticsScreen(navController = navController)
        }
        composable("post_requirements") {
            PostRequirementScreen(navController)
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
        composable("crop_details/{cropId}") { backStackEntry ->
            val cropId = backStackEntry.arguments?.getString("cropId") ?: "N/A"
            CropDetailsScreen(cropId = cropId, navController = navController)
        }
        composable("offer_contract/{cropType}/{farmerName}") { backStackEntry ->
            OfferContractScreen(
                cropType = backStackEntry.arguments?.getString("cropType") ?: "",
                farmerName = backStackEntry.arguments?.getString("farmerName") ?: "",
                navController = navController
            )
        }
        composable("notifications") {
            NotificationsScreen(navController = navController)
        }
        composable("payment_details/{paymentId}") { backStackEntry ->
            val paymentId = backStackEntry.arguments?.getString("paymentId") ?: "N/A"
            PaymentDetailsScreen(paymentId = paymentId, navController = navController)
        }
        composable("contact_farmer") { ContactFarmerScreen() }
        composable("negotiate_price") { NegotiatePriceScreen() }
        composable("buyer_contract_screen") {
            BuyerContractsScreen(
                navigateToContractDetails = { contractId ->
                    navController.navigate("contract_details/$contractId")
                }
            )
        }
        composable("confirmation_screen/{contractId}") { backStackEntry ->
            val contractId = backStackEntry.arguments?.getString("contractId") ?: "N/A"
            ConfirmationScreen(navController = navController, contractId = contractId)
        }
    }
}
