package com.rabindra.farmconnect.ui.screens

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R

@Composable
fun AddCropScreen(navController: NavHostController) {
    var cropType by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var harvestDate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Add Crop", style = MaterialTheme.typography.headlineLarge)

        // Crop Type Input Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Crop Type", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = cropType,
                onValueChange = { cropType = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        // Quantity Input Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Quantity (kg)", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = quantity,
                onValueChange = { quantity = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        // Price Input Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Price (per kg)", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = price,
                onValueChange = { price = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        // Harvest Date Input Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Harvest Date", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = harvestDate,
                onValueChange = { harvestDate = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        // Location Input Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Location", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        // Upload image button
        Button(onClick = {
            // Replace this with actual image upload logic later
            imageUri = "placeholder_image_uri" // Placeholder for selected image URI
        }) {
            Text("Upload Image")
        }

        // Show selected image (if any)
        if (imageUri != null) {
            Image(
                painter = painterResource(id = R.drawable.img), // Placeholder image
                contentDescription = "Selected Image",
                modifier = Modifier.size(100.dp).padding(8.dp)
            )
        }

        // Submit button
        Button(
            onClick = {
                // Save data here (you will add database later)
                navController.popBackStack()
                // Show success notification
                // Example: Toast or Snackbar
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Submit")
        }
    }
}
