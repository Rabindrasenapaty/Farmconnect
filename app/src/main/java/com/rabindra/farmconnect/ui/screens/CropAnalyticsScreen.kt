package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlin.math.cos
import kotlin.math.sin

// Data class for Crop Analytics
data class CropAnalyticsData(
    val cropName: String,
    val price: String,
    val demandTrend: Float,
    val harvestedAmount: Float
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
                title = { Text("Crop Analytics", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFBC6C25)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFEFAE0), // Light background color 1
                            Color(0xFFDDA15E)  // Light background color 2
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Demand Trend Distribution",
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
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
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Harvested Products",
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
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
                    }
                }

                item {
                    Text(
                        text = "Crop Details",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(cropAnalyticsList) { crop ->
                    CropAnalyticsCard(crop)
                }
            }
        }
    }
}

@Composable
fun DemandPieChart(cropAnalyticsList: List<CropAnalyticsData>) {
    val totalDemand = cropAnalyticsList.sumOf { it.demandTrend.toDouble() }.toFloat()
    val colors = listOf(Color(0xFFE57373), Color(0xFF64B5F6), Color(0xFF81C784), Color(0xFF4DD0E1), Color(0xFFBA68C8), Color(0xFFFFD54F))

    Canvas(modifier = Modifier.fillMaxSize()) {
        var startAngle = 0f
        cropAnalyticsList.forEachIndexed { index, crop ->
            val sweepAngle = (crop.demandTrend / totalDemand) * 360f
            var color = colors[index % colors.size]

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )

            val angleInRadians = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
            val labelRadius = size.width * 0.35f
            val labelX = (size.center.x + labelRadius * cos(angleInRadians)).toFloat()
            val labelY = (size.center.y + labelRadius * sin(angleInRadians)).toFloat()

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    crop.cropName,
                    labelX,
                    labelY,
                    android.graphics.Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 40f
                        color = Color.Black
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
        val axisPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            strokeWidth = 4f
        }
        val textPaint = android.graphics.Paint().apply {
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = 40f
            color = android.graphics.Color.BLACK
        }

        // Draw axes
        drawContext.canvas.nativeCanvas.apply {
            drawLine(0f, size.height, chartWidth, size.height, axisPaint)
            drawLine(0f, size.height, 0f, size.height - chartHeight, axisPaint)
        }

        cropAnalyticsList.forEachIndexed { index, crop ->
            val barHeight = (crop.harvestedAmount / maxHarvested) * chartHeight
            val barX = barWidth * (2 * index + 1)
            val barY = size.height - barHeight

            drawRect(
                color = Color(0xFF64B5F6),
                topLeft = Offset(x = barX, y = barY),
                size = Size(barWidth, barHeight)
            )

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    crop.cropName,
                    barX + barWidth / 2,
                    size.height + 40, // Adjusted to avoid overlap
                    textPaint
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
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Crop: ${crop.cropName}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF37474F)
            )
            Text(
                text = "Market Price: ${crop.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF37474F)
            )
            Text(
                text = "Demand Trend: ${crop.demandTrend}%",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF37474F)
            )
            Text(
                text = "Harvested Amount: ${crop.harvestedAmount} Tons",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF37474F)
            )
        }
    }
}
