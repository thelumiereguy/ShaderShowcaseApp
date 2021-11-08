package com.thelumiereguy.shadershowcase.core.ui.navigation

sealed class NavScreen(val route: String) {

    object ShaderListing : NavScreen("shaderListing")

    object ShaderDetailsPage : NavScreen("shaderDetailsPage") {

        const val routeWithArgument: String = "shaderDetailsPage/{shaderId}"

        const val arg0: String = "shaderId"
    }
}