package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
@Composable
fun ChatScreen(navController: NavController, contractId: String) {
    // State for the chat message and negotiation price
    var chatMessage by remember { mutableStateOf(TextFieldValue("")) }
    var negotiationPrice by remember { mutableStateOf(TextFieldValue("")) }

    // State to show the chat messages (use more advanced management for real-time updates)
    var chatMessages = remember { mutableStateListOf("Farmer: Hello, I'd like to discuss the price.", "Contractor: Sure, what are your thoughts?") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chat with Contractor for Contract $contractId", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Chat Messages (improved for styling)
        Column(modifier = Modifier.fillMaxHeight(0.7f)) {
            chatMessages.forEach { message ->
                val isFarmerMessage = message.startsWith("Farmer:")
                Text(
                    text = message,
                    color = if (isFarmerMessage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field for the chat message (using OutlinedTextField)
        OutlinedTextField(
            value = chatMessage,
            onValueChange = { chatMessage = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Message") },
            singleLine = false,
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input field for the negotiation price
        OutlinedTextField(
            value = negotiationPrice,
            onValueChange = { negotiationPrice = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            label = { Text("Propose Price ($)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Send Button to send both chat message and negotiation price
        Button(
            onClick = {
                if (chatMessage.text.isNotEmpty()) {
                    chatMessages.add("Farmer: ${chatMessage.text}")
                    chatMessage = TextFieldValue("")  // Clear the chat message field
                }

                if (negotiationPrice.text.isNotEmpty()) {
                    chatMessages.add("Farmer proposes price: $${negotiationPrice.text}")
                    negotiationPrice = TextFieldValue("")  // Clear the price proposal field
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Send")
        }
    }
}

