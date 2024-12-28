package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class CropAnalyticsData(val cropName: String, val price: String, val demandTrend: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropAnalyticsScreen(navController: NavHostController) {
    val cropAnalyticsList = listOf(
        CropAnalyticsData("Wheat", "$5.00", "Increasing"),
        CropAnalyticsData("Rice", "$3.50", "Stable"),
        CropAnalyticsData("Corn", "$4.00", "Decreasing")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crop Analytics", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(cropAnalyticsList) { crop ->
                CropAnalyticsCard(crop)
            }
        }
    }
}

@Composable
fun CropAnalyticsCard(crop: CropAnalyticsData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Crop: ${crop.cropName}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Market Price: ${crop.price}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Demand Trend: ${crop.demandTrend}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

