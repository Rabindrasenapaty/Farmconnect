package com.rabindra.farmconnect.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavHostController

@Composable
fun AddCropScreen(navController: NavHostController) {
    var cropType by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var harvestDate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Launcher for selecting an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (uri == null) {
            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Crop",
            style = MaterialTheme.typography.headlineMedium
        )

        // Crop Type Input Field
        OutlinedTextField(
            value = cropType,
            onValueChange = { cropType = it },
            label = { Text("Crop Type") },
            modifier = Modifier.fillMaxWidth()
        )

        // Quantity Input Field
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity (kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Price Input Field
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price (per kg)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Harvest Date Input Field
        OutlinedTextField(
            value = harvestDate,
            onValueChange = { harvestDate = it },
            label = { Text("Harvest Date") },
            modifier = Modifier.fillMaxWidth()
        )

        // Location Input Field
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        // Upload Image Button
        Button(
            onClick = { galleryLauncher.launch("image/*") }
        ) {
            Text("Upload Image")
        }

        // Display selected image with delete badge (if any)
        if (imageUri != null) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { imageUri = null },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Image",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        // Submit Button
        Button(
            onClick = {
                // Input validation
                if (cropType.isBlank() || quantity.isBlank() || price.isBlank() || location.isBlank()) {
                    Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                } else {
                    // Navigate back and save data logic here
                    navController.popBackStack()
                    Toast.makeText(context, "Crop Added Successfully!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Submit")
        }
    }
}
