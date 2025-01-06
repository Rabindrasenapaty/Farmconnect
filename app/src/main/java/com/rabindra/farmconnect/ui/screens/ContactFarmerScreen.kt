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
    var messageHistory by remember { mutableStateOf(listOf<String>()) } // Holds history of messages

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
                if (message.isNotBlank()) {
                    // Handle the action of sending the message
                    messageHistory = messageHistory + "Buyer: $message" // Add buyer's message to history
                    message = "" // Clear the message input field

                    // Simulate a response from the farmer
                    messageHistory = messageHistory + "Farmer: [Farmer's Response Here]"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = message.isNotBlank() // Disable the button if the message is blank
        ) {
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Message History (now dynamic)
        Text(
            text = "Message History",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Display message history
        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            messageHistory.forEachIndexed { index, message ->
                val isBuyerMessage = message.startsWith("Buyer")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isBuyerMessage) Color(0xFFE0F7FA) else Color(0xFFF1F8E9)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(message, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

