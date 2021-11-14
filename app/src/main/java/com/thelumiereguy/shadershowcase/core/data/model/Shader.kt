package com.thelumiereguy.shadershowcase.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shader(
    val id: Int,
    val title: String,
    val description: String,
    val fragmentShader: String,
    val vertexShader: String,
) : Parcelable {
    companion object {
        fun getDefault() = Shader(
            1,
            fragmentShader = "",
            vertexShader = "",
            title = "Test Title",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        )
    }
}
