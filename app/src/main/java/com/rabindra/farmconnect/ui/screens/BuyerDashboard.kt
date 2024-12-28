package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rabindra.farmconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerDashboard(navigateToOption: (String) -> Unit) {
    // Placeholder background image painter
    val backgroundImage: Painter = painterResource(R.drawable.background) // Add this image in your `res/drawable` folder.

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image with gradient overlay
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top bar with notifications and profile
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navigateToOption("notifications") }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { navigateToOption("profile") }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            }

            // Dashboard Options
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DashboardOptionCard(
                    title = "Browse Produce",
                    icon = Icons.Default.ShoppingCart,
                    backgroundColor = Color.Green,
                    onClick = { navigateToOption("browse_produce") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DashboardOptionCard(
                    title = "Post Requirements",
                    icon = Icons.Default.AddCircle,
                    backgroundColor = Color.Blue,
                    onClick = { navigateToOption("post_requirements") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DashboardOptionCard(
                    title = "My Contracts",
                    icon = Icons.Default.List,
                    backgroundColor = Color.Red,
                    onClick = { navigateToOption("buyer_contract_screen") } // Navigate to Buyer Contracts Screen
                )

            }
        }
    }
}

@Composable
fun DashboardOptionCard(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
