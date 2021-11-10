package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@ExperimentalMaterialApi
@Composable
fun ShaderDetailListing(selectedShaderId: Int, onBackPressed: () -> Unit) {
    ProvideWindowInsets {
        val context = LocalContext.current

        val shaders = remember {
            ShaderFactory.getShadersList(context)
        }

        val scrollState = rememberLazyListState()

        LaunchedEffect(key1 = true, block = {
            scrollState.scrollToItem(selectedShaderId)
        })

        BoxWithConstraints {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize(),
                scrollState,
                flingBehavior = rememberSnapperFlingBehavior(
                    scrollState,
                ),
            ) {
                items(shaders) { shader ->
                    ShaderDetailPage(shader, modifier = Modifier.fillParentMaxSize())
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailListingPreview() {
    ShaderDetailListing(0) {}
}