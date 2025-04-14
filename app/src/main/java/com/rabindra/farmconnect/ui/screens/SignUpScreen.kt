package com.rabindra.farmconnect.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rabindra.farmconnect.R

@Composable
fun SignUpScreen(navController: NavController, userType: String?) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    // State variables for text inputs
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03572F)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_4), // Background image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.not_a_member),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email input field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(id = R.string.email_address)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password input field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm Password input field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(stringResource(id = R.string.confirm_password)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Button(
                        onClick = {
                            when {
                                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                                    Toast.makeText(
                                        context,
                                        "Please fill in all fields",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                password != confirmPassword -> {
                                    Toast.makeText(
                                        context,
                                        "Passwords do not match",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {
                                    isLoading = true
                                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->
                                            isLoading = false
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Sign-up successful!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate("login/${userType ?: "buyer"}")
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    task.exception?.message ?: "Sign-up failed",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.sign_up))
                    }
                }
            }
        }
    }
}
