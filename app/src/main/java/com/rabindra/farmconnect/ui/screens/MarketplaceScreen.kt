package com.rabindra.farmconnect.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun MarketplaceScreen(navController: NavHostController) {
    var selectedCropType by remember { mutableStateOf("Select Crop Type") }
    var locationQuery by remember { mutableStateOf("") }
    var priceRange by remember { mutableStateOf(100f) }
    var harvestDate by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    val crops = listOf(
        Crop("Tomatoes", "50", "John Doe", "Maharashtra, India", "2024-12-31", 10, R.drawable.img_1),
        Crop("Potatoes", "30", "Jane Smith", "Uttar Pradesh, India", "2024-12-20", 20, R.drawable.img_2),
        Crop("Carrots", "40", "Robert Brown", "Himachal Pradesh, India", "2024-12-25", 15, R.drawable.img_3),
        Crop("Rice", "55", "Amit Kumar", "Punjab, India", "2024-12-15", 50, R.drawable.img_5),
        Crop("Wheat", "45", "Priya Sharma", "Rajasthan, India", "2024-12-28", 60, R.drawable.img_6),
        Crop("Onions", "35", "Vikram Singh", "Tamil Nadu, India", "2024-12-22", 30, R.drawable.img_7),
        Crop("Spinach", "25", "Deepak Yadav", "Karnataka, India", "2024-12-10", 40, R.drawable.img_8),
        Crop("Cabbage", "28", "Anjali Verma", "Kerala, India", "2024-12-18", 25, R.drawable.img_9),
        Crop("Cauliflower", "50", "Ravi Patel", "Gujarat, India", "2024-12-27", 20, R.drawable.img_10),
        Crop("Chilies", "60", "Suresh Reddy", "Andhra Pradesh, India", "2024-12-30", 15, R.drawable.img_11),
        Crop("Lettuce", "45", "Neha Gupta", "Madhya Pradesh, India", "2024-12-23", 18, R.drawable.img_12),
        Crop("Green Beans", "40", "Sandeep Rawat", "Bihar, India", "2024-12-26", 35, R.drawable.img_13)
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Filter Crops", style = MaterialTheme.typography.headlineSmall)

        CropTypeDropdown(
            selectedCropType = selectedCropType,
            onCropTypeSelected = { selectedCropType = it }
        )

        OutlinedTextField(
            value = locationQuery,
            onValueChange = { locationQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Location") },
            placeholder = { Text("Enter location") }
        )

        OutlinedTextField(
            value = harvestDate,
            onValueChange = { harvestDate = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Harvest Date") },
            placeholder = { Text("Enter harvest date (YYYY-MM-DD)") },
            isError = harvestDate.isNotEmpty() && !isDateValid(harvestDate),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Minimum Quantity") },
            placeholder = { Text("Enter minimum quantity in kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Price Range: Below $${priceRange.toInt()}")
            Slider(
                value = priceRange,
                onValueChange = { priceRange = it },
                valueRange = 0f..500f
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                crops.filter { crop ->
                    (selectedCropType == "Select Crop Type" || crop.type == selectedCropType) &&
                            (locationQuery.isEmpty() || crop.location.contains(locationQuery, true)) &&
                            (harvestDate.isEmpty() || isDateRelevant(crop.harvestDate, harvestDate)) &&
                            (quantity.isEmpty() || crop.quantity >= quantity.toIntOrNull() ?: 0) &&
                            crop.price.toInt() <= priceRange
                }
            ) { crop ->
                CropCard(crop = crop, navController = navController)
            }
        }
    }
}

@Composable
fun CropTypeDropdown(
    selectedCropType: String,
    onCropTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedCropType,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = { Text("Crop Type") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf("Tomatoes", "Potatoes", "Carrots").forEach { crop ->
                DropdownMenuItem(
                    onClick = {
                        onCropTypeSelected(crop)
                        expanded = false
                    },
                    text = { Text(crop) }
                )
            }
        }
    }
}

@SuppressLint("NewApi")
fun isDateRelevant(harvestDate: String, inputDate: String): Boolean {
    return try {
        val cropDate = LocalDate.parse(harvestDate, DateTimeFormatter.ISO_DATE)
        val filterDate = if (inputDate.isNotEmpty()) {
            LocalDate.parse(inputDate, DateTimeFormatter.ISO_DATE)
        } else {
            LocalDate.now()
        }

        // Show crops harvested up to 7 days before and all future dates
        !cropDate.isBefore(filterDate.minusDays(7))
    } catch (e: DateTimeParseException) {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun isDateValid(date: String): Boolean {
    return try {
        LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

@Composable
fun CropCard(crop: Crop, navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Crop Image
            Image(
                painter = painterResource(id = crop.imageResId),
                contentDescription = "Crop Image",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Crop Details
            Column(modifier = Modifier.weight(1f)) {
                Text(crop.type, style = MaterialTheme.typography.bodyLarge)
                Text("Price: \$${crop.price}", style = MaterialTheme.typography.bodyMedium)
                Text("Farmer: ${crop.farmerName}", style = MaterialTheme.typography.bodyMedium)
                Text("Location: ${crop.location}", style = MaterialTheme.typography.bodyMedium)
                Text("Harvest Date: ${crop.harvestDate}", style = MaterialTheme.typography.bodyMedium)
                Text("Quantity: ${crop.quantity} kg", style = MaterialTheme.typography.bodyMedium)
            }

            // View Details Button
            Button(onClick = {
                navController.navigate("crop_details/${crop.type}")
            }) {
                Text("View Details")
            }

        }
    }
}

data class Crop(
    val type: String,
    val price: String,
    val farmerName: String,
    val location: String,
    val harvestDate: String,
    val quantity: Int,
    val imageResId: Int
)
