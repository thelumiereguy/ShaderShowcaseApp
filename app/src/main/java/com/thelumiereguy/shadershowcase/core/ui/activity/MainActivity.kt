package com.thelumiereguy.shadershowcase.core.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thelumiereguy.shadershowcase.core.ui.navigation.NavScreen
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen.ShaderDetailListing
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable.surfaceViewsMap
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen.ListingPage

@ExperimentalFoundationApi
@ExperimentalMaterialApi
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

    override fun onResume() {
        super.onResume()
        (0..surfaceViewsMap.size()).forEach {
            surfaceViewsMap[it]?.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        (0..surfaceViewsMap.size()).forEach {
            surfaceViewsMap[it]?.onPause()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ShadersShowcaseApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.ShaderListing.route) {
        composable(NavScreen.ShaderListing.route) {
            ListingPage { shader ->
                navController.navigate(
                    "${NavScreen.ShaderDetailsPage.route}/${shader.id}"
                )
            }
        }


        composable(
            route = NavScreen.ShaderDetailsPage.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.ShaderDetailsPage.arg0) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val shaderId =
                backStackEntry.arguments?.getInt(NavScreen.ShaderDetailsPage.arg0)
                    ?: return@composable

//            val context = LocalContext.current

//            ShaderFactory.getShadersList(context).find { it.id == shaderId }?.let { shader ->
//                ShaderDetailPage(selectedShader = shader) {
//                    navController.navigateUp()
//                }
            ShaderDetailListing(selectedShaderId = shaderId) {
                navController.navigateUp()
            }
//            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShaderShowcaseTheme {
        ShadersShowcaseApp()
    }
}