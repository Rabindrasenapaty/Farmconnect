package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FContractDetailsScreen(contractId: String, navController: NavHostController) {
    val contract = Contract("Wheat", "John Doe", "₹50,000", "1 Jan 2025 - 31 Jan 2025") // Replace with real data

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contract Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Contract Summary Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Crop: ${contract.crop}", style = MaterialTheme.typography.titleMedium)
                    Text("Farmer: ${contract.farmerName}")
                    Text("Price: ${contract.price}")
                    Text("Timeline: ${contract.timeline}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))




            Spacer(modifier = Modifier.height(16.dp))

            // Back to Contracts Button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Contracts")
            }
        }
    }}

