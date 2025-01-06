package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

    Column(modifier = Modifier.fillMaxSize()) {
        // Tabs for Active and Completed Contracts
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Contracts Based on Selected Tab
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Crop: ${contract.crop}", style = MaterialTheme.typography.titleMedium)
            Text("Farmer: ${contract.farmerName}")
            Text("Price: ${contract.price}")
            Text("Timeline: ${contract.timeline}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("View Details")
                }
                onMarkCompleted?.let {
                    TextButton(onClick = it) {
                        Text("Mark Completed")
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
