package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Simulated contract data for demonstration
val contracts = listOf(
    BuyerContract("1", "Wheat", "100 kg", "$200-$300", "Delhi", "Contractor A"),
    BuyerContract("2", "Rice", "50 kg", "$150-$200", "Mumbai", "Contractor B"),
    BuyerContract("3", "Corn", "70 kg", "$100-$150", "Chennai", "Contractor C")
)

@Composable
fun FaContractDetailsScreen(navController: NavController, contractId: String) {
    val contractDetails = remember { contracts.find { it.id == contractId } }

    contractDetails?.let {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Contract Details", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Crop Type: ${it.cropType}")
            Text("Quantity: ${it.quantity}")
            Text("Price Range: ${it.priceRange}")
            Text("Location: ${it.location}")
            Text("Contractor: ${it.contractorName}")

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
        }
    } ?: run {
        Text("Contract not found!", modifier = Modifier.padding(16.dp))
    }
}
