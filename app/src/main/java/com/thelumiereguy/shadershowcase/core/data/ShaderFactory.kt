package com.thelumiereguy.shadershowcase.core.data

import android.content.Context
import android.content.res.Resources
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.core.data.model.Shader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object ShaderFactory {

    private const val description =
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

    fun getShadersList(context: Context): List<Shader> {
        val vertexShader = context.readTextFileFromResource(R.raw.simple_vertex_shader)
        return listOf(
            Shader(
                0,
                title = "Inferno",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.inferno),
                vertexShader = vertexShader
            ),
            Shader(
                1,
                title = "Rhythm of the heart",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.rhythm_of_heart),
                vertexShader = vertexShader
            ),
            Shader(
                2,
                title = "Sine-flower",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.sine_flower),
                vertexShader = vertexShader
            ),
            Shader(
                3,
                title = "Strings attached",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.strings_attached),
                vertexShader = vertexShader
            ),
            Shader(
                4,
                title = "Jelly Springs",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.jelly_springs),
                vertexShader = vertexShader
            ),
            Shader(
                5,
                title = "Starry Shimmer",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.starry_shimmer),
                vertexShader = vertexShader
            ),
            Shader(
                6,
                title = "Polka Shade",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.polka_shade),
                vertexShader = vertexShader
            ),
            Shader(
                7,
                title = "Vortex",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.vortex),
                vertexShader = vertexShader
            ),
            Shader(
                8,
                title = "Digital DNA",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.dna),
                vertexShader = vertexShader
            ),
            Shader(
                9,
                title = "Hypnotising Spiral",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.hypnotising_spiral),
                vertexShader = vertexShader
            ),
            Shader(
                10,
                title = "Monochrome Iris",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.monochrome_iris),
                vertexShader = vertexShader
            ),
            Shader(
                11,
                title = "Raining shurikens",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.shuriken_rain),
                vertexShader = vertexShader
            ),
            Shader(
                12,
                title = "Warped Memories",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.warped_memories),
                vertexShader = vertexShader
            ),
            Shader(
                13,
                title = "Monochrome Ratio",
                description = description,
                fragmentShader = context.readTextFileFromResource(R.raw.monochrome_ratio),
                vertexShader = vertexShader
            ),
        )
    }


    private fun Context.readTextFileFromResource(
        resourceId: Int
    ): String {
        val body = StringBuilder()
        try {
            val inputStream = resources.openRawResource(resourceId)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var nextLine: String?
            while (bufferedReader.readLine().also { nextLine = it } != null) {
                body.append(nextLine)
                body.append('\n')
            }
        } catch (e: IOException) {
            throw RuntimeException(
                "Could not open resource: $resourceId", e
            )
        } catch (nfe: Resources.NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId", nfe)
        }
        return body.toString()
    }
}