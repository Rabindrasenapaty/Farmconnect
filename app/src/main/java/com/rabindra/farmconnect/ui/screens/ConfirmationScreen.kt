package com.rabindra.farmconnect.ui.screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.rabindra.farmconnect.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(navController: NavController, contractId: String) {
    var isPaymentNowClicked by remember { mutableStateOf(false) }
    var isPaymentSuccessful by remember { mutableStateOf(false) }
    var selectedPaymentMode by remember { mutableStateOf("Cash on Delivery") }
    var selectedVehicle by remember { mutableStateOf("Buyer will provide vehicle") }
    var remainingTime by remember { mutableStateOf(60000L) }
    var rotationAngle by remember { mutableStateOf(0f) }

    val timer = remember {
        object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                remainingTime = 0L
            }
        }
    }
    if (!isPaymentSuccessful) timer.start()

    var isDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(isPaymentNowClicked) {
        if (isPaymentNowClicked) {
            rotationAngle = 360f
            delay(2000)
            isPaymentSuccessful = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image with Overlay
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.img_4),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            item {
                // Top Bar
                TopAppBar(
                    title = { Text("Confirmation", style = MaterialTheme.typography.headlineSmall) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF283618),
                        titleContentColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Branding (Logo)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.farmconnectlogo),
                        contentDescription = "FarmConnect Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Payment Mode Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFAE0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Select Payment Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
                            Text(selectedPaymentMode)
                        }

                        if (isDropdownExpanded) {
                            LazyColumn {
                                items(listOf("Cash on Delivery", "Online Payment")) { mode ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedPaymentMode = mode
                                                isDropdownExpanded = false
                                            }
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedPaymentMode == mode,
                                            onClick = { selectedPaymentMode = mode }
                                        )
                                        Text(text = mode, style = MaterialTheme.typography.bodyLarge)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Vehicle Selection Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFAE0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Who will provide the vehicle?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            RadioButton(
                                selected = selectedVehicle == "Buyer will provide vehicle",
                                onClick = { selectedVehicle = "Buyer will provide vehicle" }
                            )
                            Text("Buyer will provide vehicle")
                        }
                        Row {
                            RadioButton(
                                selected = selectedVehicle == "Farmer will provide vehicle",
                                onClick = { selectedVehicle = "Farmer will provide vehicle" }
                            )
                            Text("Farmer will provide vehicle")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Button
                Button(
                    onClick = { isPaymentNowClicked = true },
                    enabled = !isPaymentNowClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(rotationZ = rotationAngle)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDA15E))
                ) {
                    Text("Complete Payment")
                }

                if (isPaymentNowClicked && !isPaymentSuccessful) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                if (isPaymentSuccessful) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Payment Successful",
                            tint = Color(0xFF606C38)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Payment Completed!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF606C38)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contract Summary
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFAE0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Contract Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Contract ID: $contractId")
                        Text("Payment Mode: $selectedPaymentMode")
                        Text("Vehicle: $selectedVehicle")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download Agreement Button
                Button(
                    onClick = { if (isPaymentSuccessful) navController.navigate("agreement_form_screen/$contractId") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC6C25))
                ) {
                    Text("View Agreement")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timer
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF915F26)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    if (!isPaymentSuccessful) {
                        Text(
                            text = "Time left for next step: ${remainingTime / 1000} seconds",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Thank You Note
                if (isPaymentSuccessful) {
                    Text(
                        text = "Thank you for your payment! Your contract will be processed shortly.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF606C38),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}