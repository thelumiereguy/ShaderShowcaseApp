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

    private var hasSetShader = false

    lateinit var renderer2: Renderer

    fun setShaderRenderer(
        renderer: Renderer
    ) {


        if (hasSetShader.not()) {
            setRenderer(
                renderer
            )
            renderer2 = renderer
        }

        hasSetShader = true
    }

    override fun onResume() {
        super.onResume()
        Timber.d("ShaderGLSurfaceView onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("ShaderGLSurfaceView onPause")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.d("ShaderGLSurfaceView onDetachedFromWindow")
    }
}