package com.rabindra.farmconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rabindra.farmconnect.ui.navigation.NavGraph
import com.rabindra.farmconnect.ui.theme.FarmconnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmconnectTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
