package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rabindra.farmconnect.R

@Composable
fun StatusTracker(status: String) {
    // Define status stages and their corresponding colors
    val statusStages = listOf("Pending", "Accepted", "In Progress", "Completed")
    val statusColors = listOf(
        Color.Gray,      // Pending color
        Color(0xFF2196F3), // Accepted color (Blue)
        Color(0xFFFFC107), // In Progress color (Amber/Yellow)
        Color.Green      // Completed color
    )

    // Define Node colors between the segments
    val nodeColor = Color(0xFFBDBDBD) // Grey color for nodes

    // Calculate the progress based on the current status
    val currentStatusIndex = statusStages.indexOf(status)
    val progress = (currentStatusIndex + 1) * 0.25f // Each stage is 25%

    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        // Background bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.LightGray, RoundedCornerShape(4.dp))
        ) {
            // Status segments
            statusStages.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            if (index <= currentStatusIndex) statusColors[index] else Color.Transparent,
                        )
                )
            }
        }

        // Status nodes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.Center)
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            statusStages.forEachIndexed { index, stage ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            if (index <= currentStatusIndex) statusColors[index] else nodeColor,
                            RoundedCornerShape(50)
                        )
                )
            }
        }

        // Current status label
        Text(
            text = "Status: $status",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 40.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
fun ContractDetailsScreen(contractId: String, navController: NavHostController) {
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
                .graphicsLayer { alpha = 0.4f } // Transparency for background image
        )

        // Overlay to fade the image
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.4f } // Slight transparency for the overlay
        )

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF388E3C) // Lighter green to match background
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Header
                    Text(
                        text = "Contract Details",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White // White text for contrast
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contract Summary
                    Text(text = "Contract ID: $contractId", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Terms: Example Terms", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Timeline: Start - End", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Parties: Buyer, Farmer", style = MaterialTheme.typography.bodyMedium, color = Color.White)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Status Tracker
                    StatusTracker(status = "In Progress") // Pass the current status here

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mark Payment Complete Button
                    Button(
                        onClick = {
                            // Logic for marking payment complete
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF81C784) // Lighter green for button
                        )
                    ) {
                        Text("Mark Payment as Complete", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Navigate Back Button
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF388E3C) // Matching green for back button
                        )
                    ) {
                        Text("Back to Contracts", color = Color.White)
                    }
                }
            }
        }
    }
}

