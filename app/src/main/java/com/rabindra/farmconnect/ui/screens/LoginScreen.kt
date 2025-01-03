package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rabindra.farmconnect.R

@Composable
fun LoginScreen(navController: NavController, userType: String?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03572F)),
        contentAlignment = Alignment.Center
    ) {
        // Background Image with fade effect
        Image(
            painter = painterResource(id = R.drawable.img_4), // Background image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

        // Centered Box with some space from the sides
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp) // Add padding from sides
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Login Form", style = MaterialTheme.typography.headlineSmall, color = Color.White)

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(), // Hide password
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Forgot Password (Styled as underlined text)
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            // Handle forgot password action here (if any) or show a message
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Button
                Button(
                    onClick = {
                        // Navigate based on userType
                        when (userType) {
                            "farmer" -> navController.navigate("farmer_dashboard")
                            "buyer" -> navController.navigate("buyer_dashboard")
                            else -> navController.navigate("welcome") // Fallback in case of an error
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Signup Navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Not a member? ",
                        color = Color.White,
                    )
                    TextButton(
                        onClick = { navController.navigate("signup/$userType") },
                    ) {
                        Text(text = "Sign up now")
                    }
                }
            }
        }
    }
}
