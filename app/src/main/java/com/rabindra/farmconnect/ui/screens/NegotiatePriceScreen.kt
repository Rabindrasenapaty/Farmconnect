package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NegotiatePriceScreen(
    cropName: String,
    quantity: String,
    initialPrice: String,
    counterOffer: String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    var offerPrice by remember { mutableStateOf("") } // User input for offer price
    val contractDetails = "$cropName, $quantity, $$initialPrice (Initial Price)" // Dynamic contract details

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Negotiate Price",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Contract Details Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Contract Details", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(contractDetails, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Offer Price Input
        OutlinedTextField(
            value = offerPrice,
            onValueChange = { offerPrice = it },
            label = { Text("Enter Your Offer Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Counter Offer Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Counter Offer", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("The seller has countered with $$counterOffer", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Accept/Reject Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onAccept) {
                Text("Accept")
            }
            Button(onClick = onReject) {
                Text("Reject")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Negotiation History (Placeholder for future use)
        Text(
            text = "Negotiation History (Placeholder)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Placeholder for negotiation messages
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Farmer: Initial offer was $$initialPrice for $quantity $cropName", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("You: Your offer was $offerPrice", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
