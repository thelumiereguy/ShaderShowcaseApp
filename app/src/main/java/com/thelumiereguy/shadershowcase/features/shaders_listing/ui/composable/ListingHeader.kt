package com.thelumiereguy.shadershowcase.features.shaders_listing.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListingHeader() {
        Column(modifier = Modifier.padding(top = 8.dp, end = 8.dp, start = 8.dp, bottom = 4.dp)) {
            Text("Shader Showcase")
            Text("@thelumiereguy")
        }
}


@Preview
@Composable
fun HeaderPreview() {
    ListingHeader()
}