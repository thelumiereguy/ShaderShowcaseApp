package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalPagerApi
@OptIn(ExperimentalSnapperApi::class)
@ExperimentalMaterialApi
@Composable
fun ShaderDetailListing(selectedShaderId: Int, onBackPressed: () -> Unit) {
    ProvideWindowInsets {
        val context = LocalContext.current

        val shaders = remember {
            ShaderFactory.getShadersList(context)
        }

        val pagerState = rememberPagerState()

        LaunchedEffect(key1 = true, block = {
            pagerState.scrollToPage(selectedShaderId)
        })

        BoxWithConstraints {
            HorizontalPager(
                shaders.size,
                modifier = Modifier
                    .fillMaxSize(),
                pagerState,
            ) { index ->
                ShaderDetailPage(shaders[index], modifier = Modifier.fillMaxSize())
            }
        }
    }
}


@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailListingPreview() {
    ShaderDetailListing(0) {}
}