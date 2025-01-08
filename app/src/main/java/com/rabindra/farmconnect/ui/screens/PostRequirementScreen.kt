
package com.rabindra.farmconnect.ui.screens
import androidx.compose.foundation.text.KeyboardOptions



import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rabindra.farmconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostRequirementScreen(navController: NavController) {
    var cropType by remember { mutableStateOf(TextFieldValue("")) }
    var quantity by remember { mutableStateOf(TextFieldValue("")) }
    var priceRange by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }

    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    val cropImages = mapOf(
        "Wheat" to R.drawable.img_7,
        "Rice" to R.drawable.img_6,
        "Tomato" to R.drawable.img_3
    )

    // Default image for unknown crops
    val defaultCropImage = R.drawable.img_4

    fun isValidInput(): Boolean {
        return cropType.text.isNotEmpty() &&
                quantity.text.isNotEmpty() && quantity.text.toIntOrNull() != null &&
                priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() != null &&
                location.text.isNotEmpty()
    }

    fun saveRequirementToUserCollection() {
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val userRequirementsRef = db.collection("users").document(user.uid).collection("requirements")

            val newRequirement = hashMapOf(
                "cropType" to cropType.text,
                "quantity" to quantity.text,
                "priceRange" to priceRange.text,
                "location" to location.text
            )

            userRequirementsRef.add(newRequirement)
                .addOnSuccessListener {
                    navController.navigateUp()
                    Toast.makeText(context, "Requirement Posted Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to post requirement", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "User is not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.5f }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val cropImageResource = cropImages[cropType.text] ?: defaultCropImage
            Image(
                painter = painterResource(id = cropImageResource),
                contentDescription = "Crop Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    TextField(
                        value = cropType,
                        onValueChange = { cropType = it },
                        label = { Text("Crop Type") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = quantity.text.isNotEmpty() && quantity.text.toIntOrNull() == null,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    if (quantity.text.isNotEmpty() && quantity.text.toIntOrNull() == null) {
                        Text(
                            "Please enter a valid quantity",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = priceRange,
                        onValueChange = { priceRange = it },
                        label = { Text("Preferred Price Range") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        isError = priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() == null,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    if (priceRange.text.isNotEmpty() && priceRange.text.toFloatOrNull() == null) {
                        Text(
                            "Please enter a valid price range",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (isValidInput()) {
                                saveRequirementToUserCollection()
                            } else {
                                errorMessage = "Please fill all fields correctly"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isValidInput()
                    ) {
                        Text("Submit")
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}