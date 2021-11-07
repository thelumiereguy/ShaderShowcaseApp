package com.thelumiereguy.shadershowcase.features.shaders_listing.data

import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.features.shaders_listing.data.model.Shader

object ShaderFactory {

    fun getShadersList() = listOf(
        Shader(
            title = "Inferno",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.inferno,
        ),
        Shader(
            title = "Rhythm of the heart",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.rhythm_of_heart,
        ),
        Shader(
            title = "Sineflower",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.sine_flower,
        ),
        Shader(
            title = "Strings attached",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.strings_attached,
        ),
        Shader(
            title = "Polka Shade",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.polka_shade,
        ),
        Shader(
            title = "Starry Shimmer",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.starry_shimmer,
        ),
        Shader(
            title = "Digital DNA",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.dna,
        ),
        Shader(
            title = "Hypnotising Spiral",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.hypnotising_spiral,
        ),
        Shader(
            title = "Monochrome Iris",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.monochrome_iris,
        ),
        Shader(
            title = "Raining shurikens",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.shuriken_rain,
        ),
        Shader(
            title = "Vortex",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.vortex,
        ),
        Shader(
            title = "Warped Memories",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.warped_memories,
        ),
        Shader(
            title = "Monochrome Ratio",
            description = "Lorem ipsum text",
            fragmentShader = R.raw.monochrome_ratio,
        ),
    )
}