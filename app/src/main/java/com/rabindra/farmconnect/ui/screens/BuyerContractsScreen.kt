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

    // Sample data: Replace this with data from your database
    val activeContracts = List(5) { index -> "Active Contract #$index" }
    val completedContracts = List(5) { index -> "Completed Contract #$index" }

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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Display Active Contracts
            if (selectedTab == 0) {
                items(activeContracts.size) { index ->
                    val contractId = "C$index"
                    Card(
                        onClick = { navigateToContractDetails(contractId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = activeContracts[index])
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = { navigateToContractDetails(contractId) }) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
            // Display Completed Contracts
            else {
                items(completedContracts.size) { index ->
                    val contractId = "C$index"
                    Card(
                        onClick = { navigateToContractDetails(contractId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = completedContracts[index])
                            Spacer(modifier = Modifier.weight(1f))
                            TextButton(onClick = { navigateToContractDetails(contractId) }) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }
}
