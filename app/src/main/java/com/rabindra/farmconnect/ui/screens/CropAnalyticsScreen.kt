package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class CropAnalyticsData(
    val cropName: String,
    val price: String,
    val demandTrend: Float, // Represents percentage demand trend (0-100)
    val harvestedAmount: Float // Represents harvested amount in metric tons
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropAnalyticsScreen(navController: NavHostController) {
    val cropAnalyticsList = listOf(
        CropAnalyticsData("Wheat", "$5.00", 30f, 20f),
        CropAnalyticsData("Rice", "$3.50", 40f, 25f),
        CropAnalyticsData("Corn", "$4.00", 20f, 15f),
        CropAnalyticsData("Barley", "$3.00", 50f, 10f),
        CropAnalyticsData("Soybean", "$6.00", 60f, 30f),
        CropAnalyticsData("Oats", "$2.50", 10f, 5f)
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
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                // Pie Chart Section
                Text(
                    text = "Demand Trend Distribution",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DemandPieChart(cropAnalyticsList)
                }
            }

            item {
                // Bar Chart Section
                Text(
                    text = "Harvested Products",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HarvestedBarChart(cropAnalyticsList)
                }
            }

            item {
                // Add Spacer before Crop Details to avoid overlap with the Bar Chart
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                // Crop Details Section
                Text(
                    text = "Crop Details",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            items(cropAnalyticsList) { crop ->
                CropAnalyticsCard(crop)
            }
        }
    }
}

@Composable
fun DemandPieChart(cropAnalyticsList: List<CropAnalyticsData>) {
    val totalDemand = cropAnalyticsList.sumOf { it.demandTrend.toDouble() }.toFloat()
    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Cyan, Color.Magenta, Color.Yellow)

    Canvas(modifier = Modifier.fillMaxSize()) {
        var startAngle = 0f
        cropAnalyticsList.forEachIndexed { index, crop ->
            val sweepAngle = (crop.demandTrend / totalDemand) * 360f
            val color = colors[index % colors.size]

            // Draw Pie Segment
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )

            // Calculate Label Position
            val angle = startAngle + sweepAngle / 2
            val radius = size.width / 3
            val labelX = (size.width / 2 + radius * kotlin.math.cos(Math.toRadians(angle.toDouble()))).toFloat()
            val labelY = (size.height / 2 + radius * kotlin.math.sin(Math.toRadians(angle.toDouble()))).toFloat()

            // Draw Crop Name and Percentage
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${crop.cropName} (${crop.demandTrend.toInt()}%)",
                    labelX,
                    labelY,
                    android.graphics.Paint().apply {
                        textSize = 32f
                         // BLACK as int
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            startAngle += sweepAngle
        }
    }
}


@Composable
fun HarvestedBarChart(cropAnalyticsList: List<CropAnalyticsData>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val chartHeight = size.height * 0.8f
        val chartWidth = size.width
        val barWidth = chartWidth / (cropAnalyticsList.size * 2)
        val maxHarvested = cropAnalyticsList.maxOf { it.harvestedAmount }

        cropAnalyticsList.forEachIndexed { index, crop ->
            val barHeight = (crop.harvestedAmount / maxHarvested) * chartHeight
            val barX = barWidth * (2 * index + 1)
            val barY = size.height - barHeight

            // Draw Bar
            drawRoundRect(
                color = Color.Blue,
                topLeft = Offset(x = barX, y = barY),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(12.dp.toPx())
            )

            // Draw Crop Name and Harvested Amount
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    crop.cropName,
                    barX + barWidth / 2,
                    size.height + 20.dp.toPx(),
                    android.graphics.Paint().apply {
                        textSize = 36f
                        color = android.graphics.Color.BLACK  // BLACK as int
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
                drawText(
                    "${crop.harvestedAmount}T",
                    barX + barWidth / 2,
                    barY - 10.dp.toPx(),
                    android.graphics.Paint().apply {
                        textSize = 32f
                        color = android.graphics.Color.DKGRAY  // DKGRAY as int
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
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
                text = "Demand Trend: ${crop.demandTrend}%",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Harvested Amount: ${crop.harvestedAmount} Tons",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
