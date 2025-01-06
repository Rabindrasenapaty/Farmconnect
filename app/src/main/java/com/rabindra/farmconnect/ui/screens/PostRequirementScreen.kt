package com.rabindra.farmconnect.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun PostRequirementScreen(navController: NavController) {
    var cropType by remember { mutableStateOf(TextFieldValue("")) }
    var quantity by remember { mutableStateOf(TextFieldValue("")) }
    var priceRange by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }

    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun isValidInput(): Boolean {
        return cropType.text.isNotEmpty() &&
                quantity.text.isNotEmpty() && quantity.text.toIntOrNull() != null &&
                priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() != null &&
                location.text.isNotEmpty()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = cropType,
            onValueChange = { cropType = it },
            label = { Text("Crop Type") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            isError = quantity.text.isNotEmpty() && quantity.text.toIntOrNull() == null
        )
        if (quantity.text.isNotEmpty() && quantity.text.toIntOrNull() == null) {
            Text("Please enter a valid quantity", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = priceRange,
            onValueChange = { priceRange = it },
            label = { Text("Preferred Price Range") },
            modifier = Modifier.fillMaxWidth(),
            isError = priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() == null
        )
        if (priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() == null) {
            Text("Please enter a valid price range", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValidInput()) {
                    // Save the requirement (to be implemented later)
                    navController.navigateUp() // Navigate back to Buyer Dashboard
                    Toast.makeText(context, "Requirement Posted Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    errorMessage = "Please fill all fields correctly"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isValidInput()
        ) {
            Text("Submit")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
