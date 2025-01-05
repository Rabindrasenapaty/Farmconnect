package com.rabindra.farmconnect.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rabindra.farmconnect.ui.screens.*

@RequiresApi(Build.VERSION_CODES.Q)
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
        composable(
            "negotiate/{cropName}/{quantity}/{initialPrice}/{counterOffer}",
            arguments = listOf(
                navArgument("cropName") { type = NavType.StringType },
                navArgument("quantity") { type = NavType.StringType },
                navArgument("initialPrice") { type = NavType.StringType },
                navArgument("counterOffer") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val cropName = backStackEntry.arguments?.getString("cropName") ?: ""
            val quantity = backStackEntry.arguments?.getString("quantity") ?: ""
            val initialPrice = backStackEntry.arguments?.getString("initialPrice") ?: ""
            val counterOffer = backStackEntry.arguments?.getString("counterOffer") ?: ""

            NegotiatePriceScreen(
                cropName = cropName,
                quantity = quantity,
                initialPrice = initialPrice,
                counterOffer = counterOffer,
                onAccept = { /* Handle Accept Action */ },
                onReject = { /* Handle Reject Action */ }
            )
        }

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
        composable("verification") {
            VerificationPage(navController = navController)
        }


        composable(
            "payment/{cropType}/{offeredPrice}/{offeredQuantity}",
            arguments = listOf(
                navArgument("cropType") { type = NavType.StringType },
                navArgument("offeredPrice") { type = NavType.StringType },
                navArgument("offeredQuantity") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val cropType = backStackEntry.arguments?.getString("cropType") ?: ""
            val offeredPrice = backStackEntry.arguments?.getString("offeredPrice") ?: "0"
            val offeredQuantity = backStackEntry.arguments?.getString("offeredQuantity") ?: "0"

            PaymentScreen(navController, cropType, offeredPrice, offeredQuantity)
        }

        composable("agreement/{paymentMethod}/{contractId}") { backStackEntry ->
            val paymentMethod = backStackEntry.arguments?.getString("paymentMethod") ?: "Credit Card"
            val contractId = backStackEntry.arguments?.getString("contractId") ?: "Unknown"
            AgreementScreen(paymentMethod = paymentMethod, contractId = contractId, navController = navController)
        }

    }
        }


