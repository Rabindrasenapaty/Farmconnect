package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfirmationScreen(navController: NavController, contractId: String) {
    var isPaymentNowClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contract Confirmation",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Now Button
        Button(
            onClick = { isPaymentNowClicked = true },
            enabled = !isPaymentNowClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Payment Now")
        }

        // Payment Successful Text with Icon
        if (isPaymentNowClicked) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .clickable {
                        navController.navigate("agreement_form_screen/$contractId")
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
    }
}

