package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R
@Composable
fun PaymentScreen(
    navController: NavHostController,
    cropType: String,
    offeredPrice: String,
    offeredQuantity: String
) {
    val totalAmount = offeredPrice.toDoubleOrNull()?.times(offeredQuantity.toDoubleOrNull() ?: 0.0) ?: 0.0

    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }
    var paymentStatus by remember { mutableStateOf<String?>(null) }
    var paymentConfirmed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Payment Details for $cropType",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Price: \$${offeredPrice} per kg", style = MaterialTheme.typography.bodyLarge)
        Text("Quantity: $offeredQuantity kg", style = MaterialTheme.typography.bodyLarge)
        Text("Total: \$${"%.2f".format(totalAmount)}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Payment Method", style = MaterialTheme.typography.bodyLarge)
        val paymentMethods = listOf("Credit Card", "Bank Transfer", "Cash on Delivery")
        paymentMethods.forEach { method ->
            PaymentMethodOption(
                method = method,
                selectedPaymentMethod = selectedPaymentMethod,
                onMethodSelected = { selectedPaymentMethod = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                paymentStatus = if (totalAmount > 0) "Payment Successful" else "Payment Failed"
                paymentConfirmed = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Payment")
        }

        paymentStatus?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = if (it == "Payment Successful") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }

        if (paymentConfirmed) {
            Button(
                onClick = {
                    navController.navigate("agreement/{paymentMethod}/{contractId}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Agreement")
            }
        }
    }
}


@Composable
fun PaymentMethodOption(
    method: String,
    selectedPaymentMethod: String,
    onMethodSelected: (String) -> Unit
) {
    val icon: Painter = when (method) {
        "Credit Card" -> painterResource(id = R.drawable.credit_card) // Placeholder icon
        "Bank Transfer" -> painterResource(id = R.drawable.transfer) // Placeholder icon
        "Cash on Delivery" -> painterResource(id = R.drawable.cash_on_delivery) // Placeholder icon
        else -> painterResource(id = R.drawable.application) // Placeholder icon
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(painter = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        RadioButton(
            selected = selectedPaymentMethod == method,
            onClick = { onMethodSelected(method) }
        )
        Text(method, modifier = Modifier.align(Alignment.CenterVertically))
    }
}
