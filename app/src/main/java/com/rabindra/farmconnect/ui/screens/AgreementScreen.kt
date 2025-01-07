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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

            // Add logo (optional)
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
                "The buyer agrees to pay the full amount upon successful delivery of the crops."
            } else {
                "The buyer has paid ₹25,000 in advance via $paymentMethod."
            }

            val content = """
                Contract Agreement
                -----------------------
                Date: 2025-01-07
                Contract ID: $contractId

                Parties Involved:
                - Farmer: Rajesh Kumar
                  Address: Village Road, Patna, Bihar
                  Contact: +91-9876543210
                  Email: rajesh.kumar@example.com
                
                - Buyer: Pooja Sharma
                  Address: 123, MG Road, Jaipur, Rajasthan
                  Contact: +91-9876543211
                  Email: pooja.sharma@example.com

                Agreement Terms:
                1. Crop Type: Wheat
                2. Quantity: 200 kg
                3. Agreed Price: ₹25,000
                4. Delivery Location: Jaipur, Rajasthan
                5. Delivery Date: 2025-01-15

                Payment Mode: $paymentMethod
                $paymentDetails

                Terms & Conditions:
                - The buyer agrees to pay the full amount upon delivery (if not prepaid).
                - The farmer ensures the quality and quantity of the crops as agreed.
                - Disputes will be resolved under the jurisdiction of Jaipur, Rajasthan courts.

                

                This contract is legally binding and has been signed by both parties.
            """.trimIndent()
            document.add(Paragraph(content))
            for (i in 1..pdfDocument.numberOfPages) {
                val page = pdfDocument.getPage(i)
                val canvas = PdfCanvas(page)

                val gs1 = com.itextpdf.kernel.pdf.extgstate.PdfExtGState()
                gs1.fillOpacity = 0.1f
                canvas.setExtGState(gs1)

                val watermarkImage = Image(logoData)
                watermarkImage.scaleToFit(300f, 300f)
                watermarkImage.setFixedPosition(
                    (page.pageSize.width - watermarkImage.imageScaledWidth) / 2,
                    (page.pageSize.height - watermarkImage.imageScaledHeight) / 2
                )
                document.add(watermarkImage)
            }
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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Contract Agreement",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFAE0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Date: 2025-01-07")
                Text("Contract ID: $contractId")
                Spacer(modifier = Modifier.height(8.dp))
                Text("**Parties Involved:**")
                Text("- Farmer: Rajesh Kumar")
                Text("  Address: Village Road, Patna, Bihar")
                Text("  Contact: +91-9876543210")
                Text("  Email: rajesh.kumar@example.com")
                Spacer(modifier = Modifier.height(4.dp))
                Text("- Buyer: Pooja Sharma")
                Text("  Address: 123, MG Road, Jaipur, Rajasthan")
                Text("  Contact: +91-9876543211")
                Text("  Email: pooja.sharma@example.com")
                Spacer(modifier = Modifier.height(8.dp))
                Text("**Agreement Terms:**")
                Text("1. Crop Type: Wheat")
                Text("2. Quantity: 200 kg")
                Text("3. Agreed Price: ₹25,000")
                Text("4. Delivery Location: Jaipur, Rajasthan")
                Text("5. Delivery Date: 2025-01-15")
                Spacer(modifier = Modifier.height(8.dp))
                Text("**Payment Details:**")
                Text(
                    text = if (paymentMethod == "Cash on Delivery") {
                        "The buyer agrees to pay the full amount upon successful delivery of the crops."
                    } else {
                        "The buyer has paid ₹25,000 in advance via $paymentMethod."
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("**Terms & Conditions:**")
                Text("- The buyer agrees to pay the full amount upon delivery (if not prepaid).")
                Text("- The farmer ensures the quality and quantity of the crops as agreed.")
                Text("- Disputes will be resolved under the jurisdiction of Jaipur, Rajasthan courts.")
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                generatePDF(context, contractId, paymentMethod)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Download Agreement as PDF")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Confirmation Screen")
        }
    }
}



