package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FarmerNotificationsScreen(navController: NavHostController) {
    val farmerNotifications = listOf(
        Notification("New Buyer Interest", "A buyer is interested in your Wheat contract.", "contract_details/4"),
        Notification("Crop Health Update", "Monitor the health of your Rice crop.", "crop_health/5"),
        Notification("Payment Pending", "Awaiting payment confirmation for your sold Corn.", "payment_details/6")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Farmer Notifications", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(farmerNotifications) { notification ->
                NotificationCard(notification = notification) {
                    navController.navigate(notification.navigationRoute)
                }
            }
        }
    }
}
