package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Simulated contract data for demonstration
val contracts = listOf(
    BuyerContract("1", "Wheat", "100 kg", "200-300", "Digapahndi", "Govind"),
    BuyerContract("2", "Rice", "50 kg", "150-200", "Aska", "Suresh"),
    BuyerContract("3", "Corn", "70 kg", "100-150", "Bhubaneswar", "Yashbant")
)

@Composable
fun FaContractDetailsScreen(navController: NavController, contractId: String) {
    // Find the contract details using the contractId
    val contractDetails = remember { contracts.find { it.id == contractId } }

    contractDetails?.let {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "Contract Details",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text("Crop Type: ${it.cropType}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Quantity: ${it.quantity}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Price Range: ${it.priceRange}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Location: ${it.location}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Contractor: ${it.contractorName}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate("chat_screen/${it.id}") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Chat")
                }
                Button(
                    onClick = { navController.navigate("confirmation_screen/${it.id}") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Confirm")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back to Contract List Button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Contract List")
            }
        }
    } ?: run {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Contract not found!",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Contract List")
            }
        }
    }
}
