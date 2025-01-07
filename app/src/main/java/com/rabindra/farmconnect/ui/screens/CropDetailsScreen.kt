package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("negotiate/${crop.type}/100/600/550")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Negotiate Price")
            }

            Spacer(modifier = Modifier.height(8.dp))

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crop not found!",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Crops List")
            }
        }
    }
}

// Data class for Crop (example)
@Preview(showBackground = true)
@Composable
fun preview() {
    CropDetailsScreen(
        cropId = "Tomatoes",
        navController = NavHostController(LocalContext.current)
    )
}