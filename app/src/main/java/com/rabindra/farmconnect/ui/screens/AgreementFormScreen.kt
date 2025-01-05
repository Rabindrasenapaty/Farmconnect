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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
fun generatePDF(context: Context, contractId: String) {
    val fileName = "Agreement_Contract_$contractId.pdf"

    try {
        // Set up MediaStore for scoped storage (Android 10+)
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/FarmConnect")
        }
        val pdfUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (pdfUri != null) {
            val outputStream: OutputStream = resolver.openOutputStream(pdfUri)!!

            // Create PdfWriter and PdfDocument
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            // Load logo from drawable
            val drawableId = context.resources.getIdentifier("farmconnectlogo", "drawable", context.packageName)
            val logoInputStream = context.resources.openRawResource(drawableId)
            val logoData = ImageDataFactory.create(logoInputStream.readBytes())
            logoInputStream.close()

            // Add logo to header
            val logoImage = Image(logoData).apply {
                scaleToFit(100f, 50f)
                setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER)
            }
            document.add(logoImage)

            // Add content to the PDF
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

            // Add watermark on all pages
            for (i in 1..pdfDocument.numberOfPages) {
                val page = pdfDocument.getPage(i)
                val canvas = PdfCanvas(page)

                // Set transparency for watermark
                val gs1 = com.itextpdf.kernel.pdf.extgstate.PdfExtGState()
                gs1.fillOpacity = 0.1f // Transparency level
                canvas.setExtGState(gs1)

                // Draw the watermark image
                val watermarkImage = Image(logoData)
                watermarkImage.scaleToFit(300f, 300f) // Adjust size
                watermarkImage.setFixedPosition(
                    (page.pageSize.width - watermarkImage.imageScaledWidth) / 2,
                    (page.pageSize.height - watermarkImage.imageScaledHeight) / 2
                )
                document.add(watermarkImage)
            }

            // Close document
            document.close()

            // Notify user of success
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
