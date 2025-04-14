package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(userType: String?) {
    val firestore = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Role-specific fields
    var farmSize by remember { mutableStateOf("") } // For farmers
    var preferredCrops by remember { mutableStateOf("") } // For buyers

    var isSaving by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf("") }

    // Fetch user profile data when the screen loads
    LaunchedEffect(userId) {
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    name = document.getString("name") ?: ""
                    email = document.getString("email") ?: ""
                    phone = document.getString("phone") ?: ""
                    address = document.getString("address") ?: ""
                    if (userType == "farmer") {
                        farmSize = document.getString("farmSize") ?: ""
                    } else if (userType == "buyer") {
                        preferredCrops = document.getString("preferredCrops") ?: ""
                    }
                }
                .addOnFailureListener {
                    feedbackMessage = "Failed to fetch profile data."
                }
        }
    }

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
        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "User Type: ${userType ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

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

        if (userType == "farmer") {
            OutlinedTextField(
                value = farmSize,
                onValueChange = { farmSize = it },
                label = { Text("Farm Size (in acres)") },
                modifier = Modifier.fillMaxWidth()
            )
        } else if (userType == "buyer") {
            OutlinedTextField(
                value = preferredCrops,
                onValueChange = { preferredCrops = it },
                label = { Text("Preferred Crops") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValidInput() && userId != null) {
                    isSaving = true
                    feedbackMessage = ""

                    val profileData = mutableMapOf(
                        "name" to name,
                        "email" to email,
                        "phone" to phone,
                        "address" to address
                    )

                    if (userType == "farmer") {
                        profileData["farmSize"] = farmSize
                    } else if (userType == "buyer") {
                        profileData["preferredCrops"] = preferredCrops
                    }

                    firestore.collection("users").document(userId)
                        .set(profileData)
                        .addOnSuccessListener {
                            isSaving = false
                            feedbackMessage = "Profile updated successfully!"
                        }
                        .addOnFailureListener {
                            isSaving = false
                            feedbackMessage = "Failed to save profile data."
                        }
                } else {
                    feedbackMessage = "Please fill in all fields correctly."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Save Changes")
            }
        }

        if (feedbackMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(feedbackMessage, color = if (feedbackMessage.contains("successfully")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}
