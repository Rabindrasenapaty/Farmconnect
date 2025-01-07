package com.rabindra.farmconnect.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rabindra.farmconnect.R

@Composable
fun LoginScreen(navController: NavController, userType: String?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF03572F)),
        contentAlignment = Alignment.Center
    ) {
        // Background Image with fade effect
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )

        // Centered Box with some space from the sides
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
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
                Text(
                    text = stringResource(R.string.login_form),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
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
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Forgot Password (Styled as underlined text)
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            // Handle forgot password navigation or action
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Error Message Display
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Login Button
                Button(
                    onClick = {
                        // Input Validation
                        when {
                            email.isBlank() -> {
                                errorMessage = "Email field is required."
                            }
                            password.isBlank() -> {
                                errorMessage = "Password field is required."
                            }
                            else -> {
                                isLoading = true
                                errorMessage = null
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        isLoading = false
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                                            // Navigate based on userType
                                            when (userType) {
                                                "farmer" -> navController.navigate("verification")
                                                "buyer" -> navController.navigate("buyer_dashboard")
                                                else -> navController.navigate("welcome") // Fallback
                                            }
                                        } else {
                                            errorMessage = task.exception?.message ?: "Login failed. Please try again."
                                        }
                                    }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(stringResource(id = R.string.login))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Signup Navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.not_a_member),
                        color = Color.White,
                    )
                    TextButton(
                        onClick = { navController.navigate("signup/$userType") },
                    ) {
                        Text(text = stringResource(R.string.sign_up_now))
                    }
                }
            }
        }
    }
}
