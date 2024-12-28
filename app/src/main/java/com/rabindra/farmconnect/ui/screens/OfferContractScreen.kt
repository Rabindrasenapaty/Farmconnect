package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun OfferContractScreen(cropType: String, farmerName: String, navController: NavHostController) {
    val offeredPrice = remember { mutableStateOf("") }
    val offeredQuantity = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Offer Contract for $cropType",
            style = MaterialTheme.typography.headlineMedium
        )
        Text("Farmer: $farmerName", style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = offeredPrice.value,
            onValueChange = { offeredPrice.value = it },
            label = { Text("Offered Price (per kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = offeredQuantity.value,
            onValueChange = { offeredQuantity.value = it },
            label = { Text("Quantity (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Handle contract creation logic
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Contract")
        }
    }
}
