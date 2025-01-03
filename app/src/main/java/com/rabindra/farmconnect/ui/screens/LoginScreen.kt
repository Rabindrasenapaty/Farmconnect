package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Login Form Title
        Text(
            text = stringResource(id = R.string.login_form),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email_address)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(), // Hide password
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Text(
            text = stringResource(id = R.string.forgot_password),
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    // Handle forgot password action here
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
            Text(stringResource(id = R.string.login))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Signup Navigation
        TextButton(
            onClick = { navController.navigate("signup/$userType") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.not_a_member))
        }
    }
}
