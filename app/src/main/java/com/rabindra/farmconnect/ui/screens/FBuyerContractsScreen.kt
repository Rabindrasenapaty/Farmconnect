package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rabindra.farmconnect.R

// Define colors
val color2 = Color(0xFF9ef01a)
val color1 = Color(0xFF38b000)

// Map crop types to images
fun getCropImage(cropType: String): Int {
    return when (cropType.lowercase()) {
        "wheat" -> R.drawable.img_5 // Replace with your wheat image resource
        "rice" -> R.drawable.img_6   // Replace with your rice image resource
        "corn" -> R.drawable.img   // Replace with your corn image resource
        else -> R.drawable.basket// Default placeholder image
    }
}

data class BuyerContract(
    val id: String,
    val cropType: String,
    val quantity: String,
    val priceRange: String,
    val location: String,
    val contractorName: String,
    var status: String = "pending"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FBuyerContractsScreen(navController: NavController) {
    val contracts = remember {
        mutableStateListOf(
            BuyerContract("1", "Wheat", "100 kg", "$200-$300", "Delhi", "Contractor A"),
            BuyerContract("2", "Rice", "50 kg", "$150-$200", "Mumbai", "Contractor B"),
            BuyerContract("3", "Corn", "70 kg", "$100-$150", "Chennai", "Contractor C")
        )
    }

    var locationQuery by remember { mutableStateOf(TextFieldValue("")) }
    var cropQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredContracts = contracts.filter { contract ->
        contract.cropType.contains(cropQuery.text, ignoreCase = true) &&
                contract.location.contains(locationQuery.text, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()
        .background(
        Brush.verticalGradient(
            colors = listOf(Color(0xFF66BB6A), Color(0xFF2E7D32))
        )
    )
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.5f } // Slight transparency
        )

        // Overlay to fade the image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f), // Fade effect at the top
                            Color.Transparent // Gradually becomes transparent
                        )
                    )
                )
        )

        // Foreground content
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(
                text = "Filter Contracts",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp)
            ) {
                // Location Filter
                OutlinedTextField(
                    value = locationQuery,
                    onValueChange = { locationQuery = it },
                    label = { Text("Search by Location", color = Color.White) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Crop Type Filter
                OutlinedTextField(
                    value = cropQuery,
                    onValueChange = { cropQuery = it },
                    label = { Text("Search by Crop Type", color = Color.White) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Display contracts
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (filteredContracts.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No contracts found",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 18.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(filteredContracts.size) { index ->
                        val contract = filteredContracts[index]
                        ContractCard(
                            contract = contract,
                            onAcceptClick = {
                                navController.navigate("FaContractDetailsScreen/${contract.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContractCard(
    contract: BuyerContract,
    onAcceptClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (contract.status == "accepted") color2 else color1,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Crop Image beside the text
            Image(
                painter = painterResource(id = getCropImage(contract.cropType)),
                contentDescription = "Crop Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Crop Type: ${contract.cropType}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Quantity: ${contract.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Price Range: ${contract.priceRange}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Location: ${contract.location}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Contractor: ${contract.contractorName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status: ${contract.status.capitalize()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = { onAcceptClick() },
                enabled = contract.status != "accepted",
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007200),
                    contentColor = Color.White
                )
            ) {
                Text("Accept")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    FBuyerContractsScreen(navController = rememberNavController())
}
