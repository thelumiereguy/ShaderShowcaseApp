package com.thelumiereguy.shadershowcase.core.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen.ListingPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShaderShowcaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ShadersShowcaseApp()
                }
            }
        }
    }
}

@Composable
fun ShadersShowcaseApp() {
    val navController = rememberNavController()
    ListingPage()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShaderShowcaseTheme {
        ShadersShowcaseApp()
    }
}