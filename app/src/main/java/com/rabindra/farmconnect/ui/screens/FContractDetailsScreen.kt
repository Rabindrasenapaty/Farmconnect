package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FContractDetailsScreen(contractId: String, navController: NavHostController) {
    var status by remember { mutableStateOf("Pending") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contract Summary
        Text(text = "Contract Summary for $contractId", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Terms: Agreement between Farmer and Buyer")
        Text("Timeline: Start Date - End Date")
        Text("Parties Involved: Farmer, Buyer")

        Spacer(modifier = Modifier.height(16.dp))

        // Status Tracker
        Text("Status: $status", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Status tracker buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { status = "Accepted" }) {
                Text("Accept")
            }
            Button(onClick = { status = "In Progress" }) {
                Text("In Progress")
            }
            Button(onClick = { status = "Completed" }) {
                Text("Complete")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to upload delivery proof (Farmer only)
        Button(onClick = {
            // Upload delivery proof logic
        }) {
            Text("Upload Delivery Proof")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back to Contracts")
        }
    }
}
