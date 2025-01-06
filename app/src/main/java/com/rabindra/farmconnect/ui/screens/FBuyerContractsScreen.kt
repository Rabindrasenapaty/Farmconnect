package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class BuyerContract(
    val id: String,
    val cropType: String,
    val quantity: String,
    val priceRange: String,
    val location: String,
    val contractorName: String,
    var status: String = "pending"
)
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Location Filter
        TextField(
            value = locationQuery,
            onValueChange = { locationQuery = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth().padding(8.dp).border(1.dp, Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Crop Type Filter
        TextField(
            value = cropQuery,
            onValueChange = { cropQuery = it },
            label = { Text("Crop Type") },
            modifier = Modifier.fillMaxWidth().padding(8.dp).border(1.dp, Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display contracts
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (filteredContracts.isEmpty()) {
                item {
                    Text(
                        text = "No contracts found",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
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

@Composable
fun ContractCard(
    contract: BuyerContract,
    onAcceptClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Crop Type: ${contract.cropType}")
            Text("Quantity: ${contract.quantity}")
            Text("Price Range: ${contract.priceRange}")
            Text("Location: ${contract.location}")
            Text("Contractor: ${contract.contractorName}")
            Text("Status: ${contract.status.capitalize()}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onAcceptClick() },
                    enabled = contract.status != "accepted"
                ) {
                    Text("Accept")
                }
            }
        }
    }
}
