package com.rabindra.farmconnect.ui.screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.rabindra.farmconnect.R
import kotlinx.coroutines.delay

@Composable
fun ConfirmationScreen(navController: NavController, contractId: String) {
    var isPaymentNowClicked by remember { mutableStateOf(false) }
    var isPaymentSuccessful by remember { mutableStateOf(false) }
    var selectedPaymentMode by remember { mutableStateOf("Cash on Delivery") }
    var selectedVehicle by remember { mutableStateOf("Buyer will provide vehicle") }
    var remainingTime by remember { mutableStateOf(60000L) }
    var rotationAngle by remember { mutableStateOf(0f) }

    // Timer for next steps (e.g., delivery countdown)
    val timer = remember {
        object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                // Handle when timer finishes
            }
        }
    }
    if (!isPaymentSuccessful) {
        timer.start()
    }

    var isDropdownExpanded by remember { mutableStateOf(false) }

    // LaunchedEffect for simulating payment
    LaunchedEffect(isPaymentNowClicked) {
        if (isPaymentNowClicked) {
            rotationAngle = 360f // Trigger rotation animation
            delay(2000) // Simulate a delay for payment processing
            isPaymentSuccessful = true // Simulate payment success
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Branding (Logo)
        Image(
            painter = painterResource(id = R.drawable.farmconnectlogo),
            contentDescription = "FarmConnect Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Contract Confirmation",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Mode Selection
        Text("Select Payment Mode")
        Button(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
            Text("Select Payment Mode")
        }

        // Custom dropdown using LazyColumn
        if (isDropdownExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedPaymentMode = "Cash on Delivery"
                                isDropdownExpanded = false
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cash on Delivery",
                            style = if (selectedPaymentMode == "Cash on Delivery") MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedPaymentMode = "Online Payment"
                                isDropdownExpanded = false
                            }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Online Payment",
                            style = if (selectedPaymentMode == "Online Payment") MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vehicle Selection
        Text(
            text = "Who will provide the vehicle?",
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Now Button
        Button(
            onClick = {
                isPaymentNowClicked = true
            },
            enabled = !isPaymentNowClicked,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(rotationZ = rotationAngle)
        ) {
            Text("Payment Now")
        }

        // Rotating animation and Payment Successful Text
        if (isPaymentNowClicked && !isPaymentSuccessful) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer(rotationZ = rotationAngle)
            )
        }

        // Payment Successful Text with Icon
        if (isPaymentSuccessful) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .clickable {
                        // Navigate to the Download Agreement Button
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Payment Successful",
                    tint = Color.Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Payment Successful!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green
                )
            }
        }

        // Contract Summary Section
        Spacer(modifier = Modifier.height(16.dp))
        Text("Contract Summary:")
        Column {
            Text("Contract ID: $contractId")
            Text("Payment Mode: $selectedPaymentMode")
            Text("Vehicle: $selectedVehicle")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Download Agreement Button
        Button(
            onClick = {
                // Navigate to agreement screen after payment success
                if (isPaymentSuccessful) {
                    navController.navigate("agreement_form_screen/$contractId")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Agreement")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timer for next steps
        Text(
            text = "Time left for next step: ${remainingTime / 1000} seconds",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Thank you note
        if (isPaymentSuccessful) {
            Text(
                text = "Thank you for your payment! Your contract will be processed shortly.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Green,
                textAlign = TextAlign.Center
            )
        }
    }
}