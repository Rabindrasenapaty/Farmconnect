package com.rabindra.farmconnect.ui.screens

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import java.io.OutputStream

@RequiresApi(Build.VERSION_CODES.Q)
fun generatePDF(context: Context, contractId: String, paymentMethod: String) {
    val fileName = "Agreement_Contract_$contractId.pdf"

    try {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/FarmConnect")
        }
        val pdfUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (pdfUri != null) {
            val outputStream: OutputStream = resolver.openOutputStream(pdfUri)!!
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            val drawableId = context.resources.getIdentifier("farmconnectlogo", "drawable", context.packageName)
            val logoInputStream = context.resources.openRawResource(drawableId)
            val logoData = ImageDataFactory.create(logoInputStream.readBytes())
            logoInputStream.close()

            val logoImage = Image(logoData).apply {
                scaleToFit(100f, 50f)
                setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER)
            }
            document.add(logoImage)

            val paymentDetails = if (paymentMethod == "Cash on Delivery") {
                "The buyer agrees to pay after the successful transportation of crops."
            } else {
                "The buyer has paid ₹20,000 upfront for the crops."
            }

            val content = """
                Agreement Contract Form
                -----------------------
                Date: 2025-01-04
                Contract ID: $contractId

                Parties Involved:
                - Farmer: Amit Kumar
                  Contact: +91-9876543210
                - Buyer: Vikram Singh
                  Contact: +91-9876543211

                Agreement Terms:
                1. Crop Type: Wheat
                2. Quantity: 100 kg
                3. Agreed Price: ₹20,000
                4. Delivery Location: Delhi
                5. Delivery Date: 2025-01-10

                Payment Mode: $paymentMethod
                $paymentDetails

                Terms & Conditions:
                - The buyer agrees to pay the full amount upon delivery.
                - The farmer ensures the quality of the crops as agreed.
                - Disputes will be resolved under the jurisdiction of Delhi courts.

                Signature Section:
                Farmer's Signature: _______________  Date: ____________
                Buyer's Signature: _______________  Date: ____________

                This contract is legally binding and has been signed by both parties.
            """.trimIndent()
            document.add(Paragraph(content))

            document.close()
            Toast.makeText(context, "PDF generated successfully!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Error creating file URI.", Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error generating PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AgreementScreen(paymentMethod: String, contractId: String, navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                - Farmer: Amit Kumar
                  Contact: +91-9876543210
                - Buyer: Vikram Singh
                  Contact: +91-9876543211

                **Agreement Terms:**
                1. Crop Type: Wheat
                2. Quantity: 100 kg
                3. Agreed Price: ₹20,000
                4. Delivery Location: Delhi
                5. Delivery Date: 2025-01-10

                **Payment Details:**
                ${if (paymentMethod == "Cash on Delivery") {
                "The buyer agrees to pay after the successful transportation of crops."
            } else {
                "The buyer has paid ₹20,000 for the crops upfront via $paymentMethod."
            }}

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

        // Button to download the agreement as a PDF
        Button(
            onClick = {
                generatePDF(context, contractId)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Download Agreement as PDF")
        }

        // Button to navigate back to the confirmation screen
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Confirmation Screen")
        }
    }
}

