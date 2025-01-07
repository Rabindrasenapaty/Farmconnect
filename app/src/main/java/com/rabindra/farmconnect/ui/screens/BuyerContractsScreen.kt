package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rabindra.farmconnect.R

@Composable
fun BuyerContractsScreen(navigateToContractDetails: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Active Contracts", "Completed Contracts")

    // Sample contract data
    val activeContracts = remember { mutableStateListOf(
        Contract("Wheat", "John Doe", "₹50,000", "1 Jan 2025 - 31 Jan 2025"),
        Contract("Rice", "Jane Smith", "₹30,000", "15 Jan 2025 - 20 Jan 2025")
    ) }
    val completedContracts = remember { mutableStateListOf(
        Contract("Corn", "Alice Brown", "₹40,000", "1 Dec 2024 - 15 Dec 2024")
    ) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image with Fade Effect
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.6f },
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        // Overlay Color for a Fading Effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Tabs for Active and Completed Contracts
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (selectedTab == index) MaterialTheme.colorScheme.onPrimary else Color.Gray
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Contracts Based on Selected Tab
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (selectedTab == 0) {
                    items(activeContracts.size) { index ->
                        ContractCard(
                            contract = activeContracts[index],
                            onViewDetails = { navigateToContractDetails("Active-$index") },
                            onMarkCompleted = {
                                val completedContract = activeContracts.removeAt(index)
                                completedContracts.add(completedContract)
                            }
                        )
                    }
                } else {
                    items(completedContracts.size) { index ->
                        ContractCard(
                            contract = completedContracts[index],
                            onViewDetails = { navigateToContractDetails("Completed-$index") },
                            onMarkCompleted = null // Completed contracts don't need this action
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContractCard(
    contract: Contract,
    onViewDetails: () -> Unit,
    onMarkCompleted: (() -> Unit)? // Nullable if marking is not needed
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Crop: ${contract.crop}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Farmer: ${contract.farmerName}", style = MaterialTheme.typography.bodyLarge)
            Text("Price: ${contract.price}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            Text("Timeline: ${contract.timeline}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("View Details", color = MaterialTheme.colorScheme.primary)
                }
                onMarkCompleted?.let {
                    TextButton(onClick = it) {
                        Text("Mark Completed", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}

data class Contract(
    val crop: String,
    val farmerName: String,
    val price: String,
    val timeline: String
)


