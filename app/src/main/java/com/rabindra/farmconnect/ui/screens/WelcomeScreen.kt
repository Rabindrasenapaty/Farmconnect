package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rabindra.farmconnect.R

@Composable
fun WelcomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.background(Color.Magenta)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        ) {

        }
        // App Title
        Text(text = "Welcome to FarmConnect", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Display image with a fallback in case of errors
        Image(
            painter = painterResource(id = R.drawable.farmconnectlogo), // Ensure app_logo exists
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )

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

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    WelcomeScreen(navController = rememberNavController())
}
