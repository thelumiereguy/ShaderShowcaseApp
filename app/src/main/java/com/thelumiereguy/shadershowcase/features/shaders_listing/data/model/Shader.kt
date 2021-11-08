package com.thelumiereguy.shadershowcase.features.shaders_listing.data.model

import android.os.Parcelable
import com.thelumiereguy.shadershowcase.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shader(
    val id: Int,
    val title: String,
    val description: String,
    val fragmentShader: Int,
) : Parcelable {
    companion object {
        fun getDefault() = Shader(
            1,
            fragmentShader = R.raw.starry_shimmer,
            title = "Test Title",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        )
    }
}
