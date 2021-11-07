package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.ProvideWindowInsets
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.ShaderFactory
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader
import com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable.ListingHeader

@Composable
fun ListingPage() {
    ProvideWindowInsets {

        val shaders = remember {
            ShaderFactory.getShadersList()
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            item {
                ListingHeader()
            }

            items(shaders) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(0.6f)
                        .padding(all = 8.dp),
                ) {
                    Box {
                        AndroidView(factory = {
                            ShaderGLSurfaceView(it)
                        }) {
                            it.setShader(
                                item.fragmentShader
                            )

                        }
                    }
                    Text(
                        item.title, modifier = Modifier
                            .padding(all = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ListPreview() {
    ListingPage()
}