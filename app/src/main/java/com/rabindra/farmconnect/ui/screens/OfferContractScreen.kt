package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
@Composable
fun OfferContractScreen(cropType: String, farmerName: String, navController: NavHostController) {
    val offeredPrice = remember { mutableStateOf("") }
    val offeredQuantity = remember { mutableStateOf("") }
    val contractStatus = remember { mutableStateOf<String?>(null) }

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (offeredPrice.value.isNotEmpty() && offeredQuantity.value.isNotEmpty()) {
                        contractStatus.value = "accepted"
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = offeredPrice.value.isNotEmpty() && offeredQuantity.value.isNotEmpty()
            ) {
                Text("Accept")
            }

            Button(
                onClick = {
                    if (offeredPrice.value.isNotEmpty() && offeredQuantity.value.isNotEmpty()) {
                        contractStatus.value = "rejected"
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = offeredPrice.value.isNotEmpty() && offeredQuantity.value.isNotEmpty()
            ) {
                Text("Reject")
            }
        }

        if (contractStatus.value == "accepted") {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate(
                        "payment/$cropType/${offeredPrice.value}/${offeredQuantity.value}"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Payment Now")
            }
        }

        if (contractStatus.value == "rejected") {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "The farmer has rejected the offer.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

