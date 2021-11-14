package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.*
import com.google.android.material.math.MathUtils
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.core.data.ShaderFactory
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable.ListingCard
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlin.math.absoluteValue

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

        Box(
            contentAlignment = Alignment.CenterEnd
        ) {

            val pagerState = rememberPagerState()

            VerticalPager(
                count = shaders.size,
                state = pagerState,
                itemSpacing = 200.dp,
                key = { index -> shaders[index].title },
            ) { index ->

                val shader = remember {
                    shaders[index]
                }

                ListingCard(
                    shader,
                    modifier = Modifier,
                    onShaderSelected
                )


                Surface(color = MaterialTheme.colors.background.copy(alpha = 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                        .clickable {
                            onShaderSelected(shader)
                        }
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                            alpha = MathUtils.lerp(
                                1f,
                                0f,
                                1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }) {}
            }

            VerticalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(20.dp),
                indicatorShape = RoundedCornerShape(3.dp),
                indicatorHeight = 8.dp,
                indicatorWidth = 5.dp,
                activeColor = PrimaryTextColor,
                inactiveColor = PrimaryTextColor.copy(alpha = 0.4f)
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