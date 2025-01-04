package com.rabindra.farmconnect.ui.screens

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import java.io.File
import java.io.FileOutputStream

@Composable
fun AgreementFormScreen(navController: NavController, contractId: String, context: Context) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Agreement Contract Form",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                **Agreement Details**
                Date: 2025-01-04
                Contract ID: $contractId

                **Parties Involved:**
                - Farmer: John Doe
                  Contact: +91-9876543210
                - Buyer: Jane Smith
                  Contact: +91-9876543211

                **Agreement Terms:**
                1. Crop Type: Wheat
                2. Quantity: 100 kg
                3. Agreed Price: ₹20,000
                4. Delivery Location: Delhi
                5. Delivery Date: 2025-01-10

                **Terms & Conditions:**
                - The buyer agrees to pay the full amount upon delivery.
                - The farmer ensures the quality of the crops as agreed.
                - Disputes will be resolved under the jurisdiction of Delhi courts.
                
                **Signature Section:**
                Farmer's Signature: _______________  Date: ____________
                Buyer's Signature: _______________  Date: ____________

                This contract is legally binding and has been signed by both parties.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Download PDF Button
        Button(
            onClick = {
                generatePDF(context, contractId)
                Toast.makeText(context, "Agreement PDF downloaded!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Download Agreement as PDF")
        }

        // Back to Confirmation Screen
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Confirmation Screen")
        }
    }
}

// Function to Generate PDF
fun generatePDF(context: Context, contractId: String) {
    val fileName = "Agreement_Contract_$contractId.pdf"
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        fileName
    )
    val fileOutputStream = FileOutputStream(file)

    val content = """
        Agreement Contract Form
        -----------------------
        Date: 2025-01-04
        Contract ID: $contractId

        Parties Involved:
        - Farmer: John Doe
          Contact: +91-9876543210
        - Buyer: Jane Smith
          Contact: +91-9876543211

        Agreement Terms:
        1. Crop Type: Wheat
        2. Quantity: 100 kg
        3. Agreed Price: ₹20,000
        4. Delivery Location: Delhi
        5. Delivery Date: 2025-01-10

        Terms & Conditions:
        - The buyer agrees to pay the full amount upon delivery.
        - The farmer ensures the quality of the crops as agreed.
        - Disputes will be resolved under the jurisdiction of Delhi courts.
        
        Signature Section:
        Farmer's Signature: _______________  Date: ____________
        Buyer's Signature: _______________  Date: ____________

        This contract is legally binding and has been signed by both parties.
    """.trimIndent()

    fileOutputStream.write(content.toByteArray())
    fileOutputStream.close()
}
