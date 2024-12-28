package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R

@Composable
fun CropDetailsScreen(cropId: String, navController: NavHostController) {
    // Placeholder crops list (simulate fetching from database or repository)
    val crops = listOf(
        Crop("Tomatoes", "50", "John Doe", "California", "2024-12-31", 10, R.drawable.img_1),
        Crop("Potatoes", "30", "Jane Smith", "Texas", "2024-12-20", 20, R.drawable.img_2),
        Crop("Carrots", "40", "Robert Brown", "Florida", "2024-12-25", 15, R.drawable.img_3),
        Crop("Tomatoes", "50", "John Doe", "California", "2024-12-31", 10, R.drawable.img_1),
        Crop("Potatoes", "30", "Jane Smith", "Texas", "2024-12-20", 20, R.drawable.img_2),
        Crop("Carrots", "40", "Robert Brown", "Florida", "2024-12-25", 15, R.drawable.img_3)
    )

    val crop = crops.find { it.type == cropId } // Find crop by ID (here, crop type is used as ID)

    if (crop != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crop Details",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Image(
                painter = painterResource(id = crop.imageResId),
                contentDescription = "Crop Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )

            Text("Type: ${crop.type}", fontSize = 20.sp)
            Text("Price: \$${crop.price}", fontSize = 20.sp)
            Text("Quantity: ${crop.quantity} kg", fontSize = 20.sp)
            Text("Harvest Date: ${crop.harvestDate}", fontSize = 20.sp)
            Text("Location: ${crop.location}", fontSize = 20.sp)
            Text("Farmer: ${crop.farmerName}", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("contact_farmer") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Contact Farmer")
            }


            Button(
                onClick = { navController.navigate("negotiate_price") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Negotiate Price")
            }


            Button(
                onClick = {
                    navController.navigate("offer_contract/${crop.type}/${crop.farmerName}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Offer Contract")
            }
        }
    } else {
        Text("Crop not found!", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
    }
}
