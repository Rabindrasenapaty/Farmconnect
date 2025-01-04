package com.rabindra.farmconnect.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rabindra.farmconnect.R
import java.util.*


@Composable
fun WelcomeScreen(navController: NavController, context: Context) {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = {3})
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when(page) {
            0 -> Screen1(navController, color = Color(0xFFFEFAE0))
            1 -> Screen2(navController, color = Color(0xFFFEFAE0))
            2 -> Screen3(navController, context)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFFBC6C25) else Color(0xFFDDA15E))
                )
            }
        }
    }
}

@Composable
fun Screen1(navController: NavController, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        // Background Image with Fade Effect
        Image(
            painter = painterResource(id = R.drawable.img_4), // Background image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title Text
            Text(
                text = "Welcome to FarmConnect!",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF283618),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle Text
            Text(
                text = "Empowering Farmers, Connecting Buyers",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF283618),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Centered Image
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "Farm Connect Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Screen2(navController: NavController, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        // Background Image with Fade Effect
        Image(
            painter = painterResource(id = R.drawable.img_4), // Background image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(32.dp) // Add padding from sides
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center the content vertically
        ) {
            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.img2),
                contentDescription = "FarmConnect Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Features Title
            Text(
                text = "FarmConnect Features:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Features List
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeatureItem("✔ Seamless buying and selling of agricultural products")
                FeatureItem("✔ Secure payment gateway")
                FeatureItem("✔ Real-time chat between buyers and farmers")
                FeatureItem("✔ AI-based crop recommendation system")
                FeatureItem("✔ Transparent pricing for better deals")
            }
        }
    }
}

@Composable
fun FeatureItem(feature: String) {
    Text(
        text = feature,
        fontSize = 16.sp,
        fontFamily = FontFamily.Serif,
        color = Color.White,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}


@Composable
fun Screen3(navController: NavController, context: Context) {
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
            .background(Color(0xFFFEFAE0)) // Match the background color for consistency
    ) {
        // Background Image with Fade Effect
        Image(
            painter = painterResource(id = R.drawable.img_4), // Background image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            alpha = 0.3f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

        // Main Content Box
        Box(
            modifier = Modifier
                .padding(32.dp) // Add padding from sides
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp)
                .align(Alignment.Center) // Center the content
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // App Title
                Text(
                    text = context.getString(R.string.app_name),
                    fontSize = 24.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Display Image
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
        }

        // Top Language Icon
        Icon(
            painter = painterResource(id = R.drawable.language),
            contentDescription = "Change Language",
            modifier = Modifier
                .size(40.dp)
                .padding(top = 10.dp,end = 10.dp)
                .align(Alignment.TopEnd)
                .clickable { isLanguageDialogOpen = true },
            tint = Color.Unspecified
        )

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