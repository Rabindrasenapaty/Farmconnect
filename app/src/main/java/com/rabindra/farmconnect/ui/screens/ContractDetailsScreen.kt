package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ContractDetailsScreen(contractId: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Header
        Text(
            text = "Contract Details",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contract Summary
        Text(text = "Contract ID: $contractId")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Terms: Example Terms")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Timeline: Start - End")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Parties: Buyer, Farmer")

        Spacer(modifier = Modifier.height(16.dp))

        // Status Tracker
        Text(text = "Status Tracker:")
        Text(text = "Pending -> Accepted -> In Progress -> Completed")

        Spacer(modifier = Modifier.height(16.dp))

        // Mark Payment Complete Button
        Button(
            onClick = {
                // Logic for marking payment complete
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mark Payment as Complete")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigate Back Button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Contracts")
        }
    }
}
