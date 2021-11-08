package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable.ListingCard
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@ExperimentalFoundationApi
@OptIn(ExperimentalSnapperApi::class)
@Composable
fun ListingPage(onShaderSelected: (Shader) -> Unit) {
    ProvideWindowInsets {

        val context = LocalContext.current

        val shaders = remember {
            ShaderFactory.getShadersList(context)
        }

        val scrollState = rememberLazyListState()

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            scrollState,
            flingBehavior = rememberSnapperFlingBehavior(scrollState)
        ) {

            items(shaders, key = {
                it.id
            }) { item ->
                Box(
                    modifier = Modifier
                        .fillParentMaxHeight()
                ) {
                    ListingCard(item, onShaderSelected)
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Preview
@Composable
fun ListPreview() {
    ListingPage {

    }
}