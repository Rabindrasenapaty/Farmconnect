package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
@Composable
fun ProfileScreen(userType: String?) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Role-specific fields
    var farmSize by remember { mutableStateOf("") } // For farmers
    var preferredCrops by remember { mutableStateOf("") } // For buyers

    var isSaving by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf("") }

    // Validation for required fields
    fun isValidInput(): Boolean {
        return name.isNotEmpty() &&
                email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                phone.isNotEmpty() && phone.all { it.isDigit() } &&
                address.isNotEmpty() && (userType != "farmer" || farmSize.isNotEmpty()) && (userType != "buyer" || preferredCrops.isNotEmpty())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Header
        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display user type
        Text(text = "User Type: ${userType ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Common Fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )

        if (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Text("Please enter a valid email address", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            isError = phone.isNotEmpty() && phone.any { !it.isDigit() }
        )

        if (phone.isNotEmpty() && phone.any { !it.isDigit() }) {
            Text("Please enter a valid phone number", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Role-Specific Fields
        if (userType == "farmer") {
            OutlinedTextField(
                value = farmSize,
                onValueChange = { farmSize = it },
                label = { Text("Farm Size (in acres)") },
                modifier = Modifier.fillMaxWidth(),
                isError = farmSize.isNotEmpty() && farmSize.toFloatOrNull() == null
            )

            if (farmSize.isNotEmpty() && farmSize.toFloatOrNull() == null) {
                Text("Please enter a valid farm size", color = MaterialTheme.colorScheme.error)
            }
        } else if (userType == "buyer") {
            OutlinedTextField(
                value = preferredCrops,
                onValueChange = { preferredCrops = it },
                label = { Text("Preferred Crops") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                if (isValidInput()) {
                    // Start saving logic (e.g., saving to a database or API)
                    isSaving = true
                    feedbackMessage = "Saving..."
                    // Simulate save operation (replace with actual logic)
                    // After saving, update feedback
                    isSaving = false
                    feedbackMessage = "Profile updated successfully!"
                } else {
                    feedbackMessage = "Please fill in all fields correctly."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidInput() && !isSaving
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Save Changes")
            }
        }

        // Feedback message
        if (feedbackMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(feedbackMessage, color = if (feedbackMessage.contains("successfully")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}
