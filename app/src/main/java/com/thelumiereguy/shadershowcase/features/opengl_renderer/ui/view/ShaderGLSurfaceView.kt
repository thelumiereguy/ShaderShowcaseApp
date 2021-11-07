package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class ShaderGLSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : GLSurfaceView(context, attrs) {

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)


        preserveEGLContextOnPause = true

        /**
         * To enable drawing only when needed.
         * To redraw, use [requestRender]
         */
//        renderMode = RENDERMODE_WHEN_DIRTY
    }

    fun setShader(
        fragmentShaderResource: Int,
        vertexShaderResource: Int = R.raw.simple_vertex_shader,
    ) {
        val fragmentShader = readTextFileFromResource(fragmentShaderResource)
        val vertexShader = readTextFileFromResource(vertexShaderResource)

        setRenderer(
            ShaderRenderer(
                fragmentShader,
                vertexShader,
            )
        )
    }


    private fun readTextFileFromResource(
        resourceId: Int
    ): String {
        val body = StringBuilder()
        try {
            val inputStream = context.resources.openRawResource(resourceId)
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
        } catch (nfe: NotFoundException) {
            throw RuntimeException("Resource not found: $resourceId", nfe)
        }
        return body.toString()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timber.d("ShaderGLSurfaceView onAttach")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("ShaderGLSurfaceView onDetachedFromWindow")
    }
}