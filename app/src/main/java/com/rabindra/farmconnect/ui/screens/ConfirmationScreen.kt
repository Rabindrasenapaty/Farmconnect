package com.rabindra.farmconnect.ui.screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
    var remainingTime by remember { mutableLongStateOf(60000L) }
    var rotationAngle by remember { mutableFloatStateOf(0f) }

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

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            // Branding (Logo)
            Image(
                painter = painterResource(id = R.drawable.farmconnectlogo),
                contentDescription = "FarmConnect Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            // Vehicle Selection Section
            Card(
                modifier = Modifier.fillMaxWidth(),
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

            // Payment Button
            Button(
                onClick = { isPaymentNowClicked = true },
                enabled = !isPaymentNowClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(rotationZ = rotationAngle),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDDA15E))
            ) {
                Text("Complete Payment")
            }

            if (isPaymentNowClicked && !isPaymentSuccessful) {
                CircularProgressIndicator()
            }

            if (isPaymentSuccessful) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp) // Add padding from sides
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Payment Successful",
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Payment Completed!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Green
                        )
                    }
                }
            }

            // Contract Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
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

            // Download Agreement Button
            Button(
                onClick = { if (isPaymentSuccessful) navController.navigate("agreement_form_screen/$contractId") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC6C25))
            ) {
                Text("View Agreement")
            }

            // Timer
            if (!isPaymentSuccessful) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF915F26)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "Time left for next step: ${remainingTime / 1000} seconds",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            // Thank You Note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp) // Add padding from sides
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isPaymentSuccessful) {
                    Text(
                        text = "Thank you for using our app",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
