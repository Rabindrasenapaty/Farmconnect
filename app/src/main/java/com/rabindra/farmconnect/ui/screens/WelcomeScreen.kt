package com.rabindra.farmconnect.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rabindra.farmconnect.R
import java.util.*

@Composable
fun WelcomeScreen(navController: NavController, context: Context) {
    var isLanguageDialogOpen by remember { mutableStateOf(false) }

    // Access SharedPreferences and ensure a default language is set
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val defaultLanguage = "en"
    if (!sharedPreferences.contains("selected_language")) {
        sharedPreferences.edit().putString("selected_language", defaultLanguage).apply()
        setAppLocale(context, defaultLanguage) // Set default locale
    }

    val selectedLanguage = remember {
        mutableStateOf(sharedPreferences.getString("selected_language", defaultLanguage) ?: defaultLanguage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Language Icon
        Icon(
            painter = painterResource(id = R.drawable.language),
            contentDescription = "Change Language",
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)
                .clickable { isLanguageDialogOpen = true },
            tint = Color.Unspecified
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Title
            Text(text = context.getString(R.string.app_name), fontSize = 24.sp)

            Spacer(modifier = Modifier.height(20.dp))

            // Display image
            Image(
                painter = painterResource(id = R.drawable.farmconnectlogo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sell Button
            Button(
                onClick = { navController.navigate("login/farmer") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(context.getString(R.string.sell))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Buy Button
            Button(
                onClick = { navController.navigate("login/buyer") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(context.getString(R.string.buy))
            }
        }

        if (isLanguageDialogOpen) {
            LanguageSelectionDialog(
                selectedLanguage = selectedLanguage.value,
                onDismiss = { isLanguageDialogOpen = false },
                onLanguageSelected = { language ->
                    selectedLanguage.value = language
                    setAppLocale(context, language)
                    sharedPreferences.edit().putString("selected_language", language).apply()
                    isLanguageDialogOpen = false
                }
            )
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    selectedLanguage: String,
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Language") },
        text = {
            Column {
                LanguageOption(
                    languageName = "English",
                    languageCode = "en",
                    isSelected = selectedLanguage == "en",
                    onLanguageSelected
                )
                LanguageOption(
                    languageName = "हिन्दी",
                    languageCode = "hi",
                    isSelected = selectedLanguage == "hi",
                    onLanguageSelected
                )
                LanguageOption(
                    languageName = "ଓଡ଼ିଆ",
                    languageCode = "or",
                    isSelected = selectedLanguage == "or",
                    onLanguageSelected
                )
            }
        },
        confirmButton = {}
    )
}

@Composable
fun LanguageOption(
    languageName: String,
    languageCode: String,
    isSelected: Boolean,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageSelected(languageCode) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = languageName)
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green
            )
        }
    }
}

fun setAppLocale(context: Context, languageCode: String) {
    val resources = context.resources
    val configuration = resources.configuration
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}