package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.core.data.ShaderFactory
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable.ShaderDetailPage
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable.SwipeButtonRow
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import timber.log.Timber

@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalSnapperApi::class)
@ExperimentalMaterialApi
@Composable
fun ShaderDetailListing(selectedShaderId: Int, onBackPressed: () -> Unit) {
    ProvideWindowInsets {

        val systemUIController = rememberSystemUiController()

        SideEffect {
            systemUIController.setStatusBarColor(
                Color.Black
            )
        }

        Scaffold {
            val context = LocalContext.current

            val shaders = remember {
                ShaderFactory.getShadersList(context)
            }

            var selectedPage by rememberSaveable {
                mutableStateOf(
                    selectedShaderId
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {

                ShaderDetailPage(
                    shaders[selectedPage],
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                )

                SwipeButtonRow(
                    selectedPage,
                    shaders.size,
                    modifier = Modifier.align(Alignment.Center)
                ) { newPageIndex ->
                    selectedPage = newPageIndex
                }

                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Transparent,
                                ),
                            )
                        )
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.abc_ic_ab_back_material
                        ),
                        contentDescription = "Icon to navigate back",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clickable {
                                onBackPressed()
                            }
                            .padding(20.dp)
                            .size(28.dp)
                    )
                }
            }

        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShaderDetailListingPreview() {
    ShaderDetailListing(1) {}
}