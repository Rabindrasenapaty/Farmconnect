package com.rabindra.farmconnect.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rabindra.farmconnect.R
import kotlinx.coroutines.delay

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isVerified.value) {
            Text("Upload Authorization Certificate", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { launcher.launch("application/pdf") },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Choose File")
            }

            pdfUri.value?.let {
                Text(
                    text = "Selected File: ${it.lastPathSegment}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (pdfUri.value != null) {
                        isUploading.value = true
                        // Simulate a fake upload process
                        simulateVerification {
                            isUploading.value = false
                            isVerified.value = true
                        }
                    }
                },
                enabled = pdfUri.value != null && !isUploading.value
            ) {
                Text("Submit for Verification")
            }

            if (isUploading.value) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.checkmark),
                contentDescription = "Verified",
                modifier = Modifier.size(100.dp)
            )
            Text("Verified Successfully", fontSize = 20.sp, color = Color.Green)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("farmer_dashboard") // Navigate to FarmerScaffold
                }
            ) {
                Text("Proceed")
            }
        }
    }
}

private  fun simulateVerification(onComplete: () -> Unit) {
    // Simulates a delay to mimic file processing

    onComplete()
}
