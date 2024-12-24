package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Title
        Text(text = "Welcome to FarmConnect", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Sell Button
        Button(
            onClick = { navController.navigate("login/farmer") }, // Pass "farmer" as userType
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sell")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Buy Button
        Button(
            onClick = { navController.navigate("login/buyer") }, // Pass "buyer" as userType
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buy")
        }
    }
}
