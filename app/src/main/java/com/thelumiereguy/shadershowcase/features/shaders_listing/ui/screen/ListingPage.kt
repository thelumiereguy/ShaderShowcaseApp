package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.VerticalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable.ListingCard
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalPagerApi
@ExperimentalFoundationApi
@OptIn(ExperimentalSnapperApi::class)
@Composable
fun ListingPage(onShaderSelected: (Shader) -> Unit) {
    ProvideWindowInsets {

        val context = LocalContext.current

        val shaders = remember {
            ShaderFactory.getShadersList(context)
        }

        val pagerState = rememberPagerState()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            VerticalPager(
                count = shaders.size / 2,
                state = pagerState,
                key = { index -> shaders[index].title },
                itemSpacing = 24.dp,
                modifier = Modifier.wrapContentHeight()
            ) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)

                ) {
                    val currentIndex = if (index == 0) 0 else index * 2
                    val nextIndex = if (index == 0) 1 else currentIndex + 1

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        ListingCard(shaders[currentIndex], onShaderSelected)
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        ListingCard(shaders[nextIndex], onShaderSelected)
                    }
                }

//                Surface(color = MaterialTheme.colors.background.copy(alpha = 1f),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.6f)
//                        .graphicsLayer {
//                            val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
//                            alpha = MathUtils.lerp(
//                                1f,
//                                0f,
//                                1f - pageOffset.coerceIn(0f, 1f)
//                            )
//                        }) {}
            }

            VerticalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@ExperimentalPagerApi
@ExperimentalFoundationApi
@Preview
@Composable
fun ListPreview() {
    ListingPage {

    }
}