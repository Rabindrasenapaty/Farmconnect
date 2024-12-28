package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FarmerContractsScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Active, 1: Completed

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            Tab(text = { Text("Active Contracts") }, selected = selectedTab == 0, onClick = { selectedTab = 0 })
            Tab(text = { Text("Completed Contracts") }, selected = selectedTab == 1, onClick = { selectedTab = 1 })
        }

        // List of contracts based on selected tab
        when (selectedTab) {
            0 -> ActiveContractsList(navController)
            1 -> CompletedContractsList(navController)
        }
    }
}

@Composable
fun ActiveContractsList(navController: NavHostController) {
    // This list can be dynamic based on data
    val contracts = listOf(
        "Contract 1" to "Pending",
        "Contract 2" to "In Progress"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        contracts.forEach { (contract, status) ->
            ContractItem(contract, status, navController)
        }
    }
}

@Composable
fun CompletedContractsList(navController: NavHostController) {
    // This list can be dynamic based on data
    val contracts = listOf(
        "Contract 3" to "Completed"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        contracts.forEach { (contract, status) ->
            ContractItem(contract, status, navController)
        }
    }
}

@Composable
fun ContractItem(contract: String, status: String, navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$contract - $status")
        Button(onClick = {
            // Navigate to contract details
            navController.navigate("contract_details/$contract")
        }) {
            Text("View Details")
        }
    }
}
