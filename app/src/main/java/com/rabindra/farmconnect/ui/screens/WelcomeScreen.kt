package com.rabindra.farmconnect.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun WelcomeScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0, initialPageOffsetFraction = 0f)
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> Screen1(Color(0xFF03572F))
            1 -> Screen2(Color(0xFF03572F))
            2 -> Screen3(navController, Color(0xFF03572F))
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
                        .background(if (isSelected) Color(0xFF27442A) else Color(0xFF549159))
                )
            }
        }
    }
}

@Composable
fun Screen1(color: Color) {
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
            modifier = Modifier.fillMaxSize()
        ) {
            // Title Text
            Text(
                text = "Welcome to FarmConnect!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle Text
            Text(
                text = "Empowering Farmers, Connecting Buyers",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White,
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
fun Screen2(color: Color) {
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
            alpha = 0.5f, // Apply fade effect by reducing opacity
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp)
            ) {// Features List
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
fun Screen3(navController: NavController, color: Color) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Title
            Text(
                text = "Welcome to FarmConnect",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Display image with a fallback in case of errors
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Buttons in a Row (Side by Side)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, // Space buttons evenly
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sell Button
                Button(
                    onClick = { navController.navigate("login/farmer") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF87CEEB), // Sky Blue
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sell", fontFamily = FontFamily.Serif)
                }

                // Buy Button
                Button(
                    onClick = { navController.navigate("login/buyer") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF7F50), // Coral Pink
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Buy", fontFamily = FontFamily.Serif)
                }
            }
        }
    }
}
