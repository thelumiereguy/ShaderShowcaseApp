package com.thelumiereguy.shadershowcase.features.shaders_listing.data

import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader

object ShaderFactory {

    private const val description =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

    fun getShadersList() = listOf(
        Shader(
            1,
            title = "Inferno",
            description = description,
            fragmentShader = R.raw.inferno,
        ),
        Shader(
            2,
            title = "Rhythm of the heart",
            description = description,
            fragmentShader = R.raw.rhythm_of_heart,
        ),
        Shader(
            3,
            title = "Sine-flower",
            description = description,
            fragmentShader = R.raw.sine_flower,
        ),
        Shader(
            4,
            title = "Strings attached",
            description = description,
            fragmentShader = R.raw.strings_attached,
        ),
        Shader(
            5,
            title = "Polka Shade",
            description = description,
            fragmentShader = R.raw.polka_shade,
        ),
        Shader(
            6,
            title = "Starry Shimmer",
            description = description,
            fragmentShader = R.raw.starry_shimmer,
        ),
        Shader(
            7,
            title = "Digital DNA",
            description = description,
            fragmentShader = R.raw.dna,
        ),
        Shader(
            8,
            title = "Hypnotising Spiral",
            description = description,
            fragmentShader = R.raw.hypnotising_spiral,
        ),
        Shader(
            9,
            title = "Monochrome Iris",
            description = description,
            fragmentShader = R.raw.monochrome_iris,
        ),
        Shader(
            10,
            title = "Raining shurikens",
            description = description,
            fragmentShader = R.raw.shuriken_rain,
        ),
        Shader(
            11,
            title = "Vortex",
            description = description,
            fragmentShader = R.raw.vortex,
        ),
        Shader(
            12,
            title = "Warped Memories",
            description = description,
            fragmentShader = R.raw.warped_memories,
        ),
        Shader(
            13,
            title = "Monochrome Ratio",
            description = description,
            fragmentShader = R.raw.monochrome_ratio,
        ),
    )
}