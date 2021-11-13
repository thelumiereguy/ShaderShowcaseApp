package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.core.ui.theme.ShaderShowcaseTheme
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable.GLShader
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader

@Composable
fun ListingCard(shader: Shader, modifier: Modifier = Modifier, onShaderSelected: (Shader) -> Unit) {

    val shaderRenderer = remember {
        ShaderRenderer().apply {
            setShaders(
                shader.fragmentShader,
                shader.vertexShader,
            )
        }
    }

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            GLShader(renderer = shaderRenderer)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            endY = 400f
                        )
                    )
            ) {

                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    shader.title,
                    color = PrimaryTextColor,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun ListCardPreview() {
    ShaderShowcaseTheme {
        ListingCard(
            Shader.getDefault()
        ) {


        }
    }
}