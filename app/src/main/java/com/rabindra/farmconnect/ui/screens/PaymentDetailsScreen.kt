package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
@Composable
fun PaymentDetailsScreen(paymentId: String, navController: NavHostController) {
    var isLoading by remember { mutableStateOf(true) }
    var paymentDetails by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Simulate loading the payment details (replace with actual logic)
    LaunchedEffect(paymentId) {
        // Simulate a delay for fetching details
        kotlinx.coroutines.delay(2000)
        // Simulate fetching data
        paymentDetails = "Amount: \$100, Status: Successful, Date: 2025-01-06" // Replace with actual data fetching logic
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Payment Details", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading payment details...", style = MaterialTheme.typography.bodyMedium)
        } else {
            if (paymentDetails != null) {
                Text("Payment ID: $paymentId")
                Spacer(modifier = Modifier.height(8.dp))
                com.itextpdf.layout.element.Text(paymentDetails!!)
            } else {
                errorMessage = "Failed to load payment details."
                Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}
