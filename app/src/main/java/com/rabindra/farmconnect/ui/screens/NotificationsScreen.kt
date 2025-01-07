package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NotificationsScreen(navController: NavHostController) {
    // Sample notification data
    val notifications = listOf(
        Notification(
            title = "Contract Approved",
            message = "Your contract for Tomatoes has been approved.",
            navigationRoute = "contract_details/1",
            isClickable = true
        ),
        Notification(
            title = "New Crop Listing",
            message = "Potatoes listed in your preferred location.",
            navigationRoute = "crop_details/2",
            isClickable = true
        ),
        Notification(
            title = "Payment Confirmation",
            message = "Payment received for the crop you sold.",
            navigationRoute = "payment_details/3",
            isClickable = true
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Notifications", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification = notification) {
                    if (notification.isClickable) {
                        navController.navigate(notification.navigationRoute)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Notification, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(
                if (notification.isClickable) Modifier.clickable { onClick() }
                else Modifier
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = notification.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = notification.message, style = MaterialTheme.typography.bodySmall)
            }
            Icon(
                imageVector = Icons.Default.Notifications, // Notification icon
                contentDescription = "Notification Icon",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

// Notification data class
data class Notification(
    val title: String,
    val message: String,
    val navigationRoute: String,
    val isClickable: Boolean
)
