package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.rabindra.farmconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FBuyerContractsScreen(navController: NavController) {
    val contracts = remember {
        mutableStateListOf(
            BuyerContract("1", "Wheat", "100 kg", "200-300", "Digapahandi", "Govind"),
            BuyerContract("2", "Rice", "50 kg", "150-200", "Aska", "Suresh"),
            BuyerContract("3", "Corn", "70 kg", "100-150", "Bhubaneswar", "Yashbant")
        )
    }

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    LaunchedEffect(user) {
        if (user != null) {
            val userRequirementsRef = db.collection("users")
                .document(user.uid)
                .collection("requirements")
            userRequirementsRef.get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { document ->
                        val cropType = document.getString("cropType") ?: ""
                        val quantity = document.getString("quantity") ?: ""
                        val priceRange = document.getString("priceRange") ?: ""
                        val location = document.getString("location") ?: ""
                        val contractorName = "Buyer"

                        val newContract = BuyerContract(
                            "new_${contracts.size}",
                            cropType,
                            quantity,
                            priceRange,
                            location,
                            contractorName
                        )
                        if (newContract !in contracts) {
                            contracts.add(newContract)
                        }
                    }
                }
        }
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredContracts = contracts.filter { contract ->
        val query = searchQuery.text.trim().lowercase()
        contract.cropType.lowercase().contains(query) ||
                contract.location.lowercase().contains(query)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_4),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp)),
                tonalElevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.1f)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search by Crop or Location", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF213F33),
                        unfocusedBorderColor = Color(0xFF000000).copy(alpha = 0.7f),
                        cursorColor = Color.Black,
                        focusedLabelColor = Color(0xFF2E7D32),
                        unfocusedLabelColor = Color(0xFF01FF7B).copy(alpha = 0.8f),
                        focusedTextColor = Color.Black
                    )
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredContracts) { contract ->
                    ContractCard(contract, navController)
                }
            }
        }
    }
}

@Composable
fun ContractCard(contract: BuyerContract, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Crop: ${contract.cropType}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Quantity: ${contract.quantity}")
            Text("Price Range: ${contract.priceRange}")
            Text("Location: ${contract.location}")
            Text("Contractor: ${contract.contractorName}")
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    navController.navigate("FaContractDetailsScreen/${contract.id}")
                }) {
                    Text("Accept")
                }
            }
        }
    }
}

data class BuyerContract(
    val id: String,
    val cropType: String,
    val quantity: String,
    val priceRange: String,
    val location: String,
    val contractorName: String
)
