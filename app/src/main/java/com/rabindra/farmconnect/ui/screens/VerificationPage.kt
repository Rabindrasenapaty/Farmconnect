package com.rabindra.farmconnect.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R

@Composable
fun VerificationPage(navController: NavHostController) {
    val pdfUri = remember { mutableStateOf<Uri?>(null) }
    val isUploading = remember { mutableStateOf(false) }
    val isVerified = remember { mutableStateOf(false) }

    // File picker for PDF
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pdfUri.value = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFEFAE0)) // Color3 as background
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )

        // Main Content
        Box(
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF03572F).copy(alpha = 0.8f)) // Color2 with transparency
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            if (!isVerified.value) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ) {
                    // Title Text
                    Text(
                        text = "Upload Authorization Certificate",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFEFAE0), // Color3
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Upload Button
                    Button(
                        onClick = { launcher.launch("application/pdf") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC6C25)), // Color5
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Choose File", fontSize = 16.sp, color = Color.White)
                    }

                    // Display Selected File
                    pdfUri.value?.let {
                        Text(
                            text = "Selected File: ${it.lastPathSegment}",
                            fontSize = 14.sp,
                            color = Color(0xFFDDA15E), // Color4
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (pdfUri.value != null) {
                                isUploading.value = true
                                simulateVerification {
                                    isUploading.value = false
                                    isVerified.value = true
                                }
                            }
                        },
                        enabled = pdfUri.value != null && !isUploading.value,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF606C38)), // Color1
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Submit for Verification", fontSize = 16.sp, color = Color.White)
                    }

                    // Upload Progress Indicator
                    if (isUploading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp),
                            color = Color(0xFFDDA15E) // Color4
                        )
                    }
                }
            } else {
                // Success State
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    VerifiedUI(
                        message = "Verified Successfully",
                        painter = painterResource(id = R.drawable.checkmark)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Proceed Button
                    Button(
                        onClick = {
                            navController.navigate("farmer_dashboard")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)), // Color1
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Proceed", fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun VerifiedUI(message: String, painter: Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Checkmark Image
        Image(
            painter = painter,
            contentDescription = "Verified Icon",
            modifier = Modifier.size(120.dp)
        )

        // Success Message
        Text(
            text = message,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White, // Color5
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

private fun simulateVerification(onComplete: () -> Unit) {
    // Simulates a delay to mimic file processing
    onComplete()
}
