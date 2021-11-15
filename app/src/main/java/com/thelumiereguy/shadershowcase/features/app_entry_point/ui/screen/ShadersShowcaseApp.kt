package com.thelumiereguy.shadershowcase.features.app_entry_point.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thelumiereguy.shadershowcase.core.ui.navigation.NavScreen
import com.thelumiereguy.shadershowcase.core.ui.navigation.composableSlide
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen.ShaderDetailListing
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen.ListingPage

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ShadersShowcaseApp() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavScreen.ShaderListing.route
    ) {
        composableSlide(
            NavScreen.ShaderListing.route,
        ) {
            ListingPage { shader ->
                navController.navigate(
                    "${NavScreen.ShaderDetailsPage.route}/${shader.id}"
                )
            }
        }


        composableSlide(
            route = NavScreen.ShaderDetailsPage.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.ShaderDetailsPage.arg0) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val shaderId =
                backStackEntry.arguments?.getInt(NavScreen.ShaderDetailsPage.arg0)
                    ?: return@composableSlide

            ShaderDetailListing(selectedShaderId = shaderId) {
                navController.navigateUp()
            }

        }
    }
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShaderShowcaseTheme {
        ShadersShowcaseApp()
    }
}