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
fun ContactFarmerScreen() {
    // State to hold the message typed by the buyer
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title of the screen
        Text(
            text = "Contact Farmer",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Informational Text
        Text(
            text = "You can send a message to the farmer to discuss further.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Message Input Field
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Enter your message here") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Send Message Button
        Button(
            onClick = {
                // Handle the action of sending the message (e.g., save to the database)
                // For now, you can just print it or show a toast
                println("Message Sent: $message")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder for message history (can be shown after integrating database)
        Text(
            text = "Message History (Placeholder)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Placeholder for showing sent and received messages (can be dynamic once the database is connected)
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Buyer: $message", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                // Placeholder for Farmer's response (this will be dynamic once the database is connected)
                Text("Farmer: [Farmer's Response Here]", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
