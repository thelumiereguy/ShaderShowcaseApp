package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable

import android.opengl.GLSurfaceView.DEBUG_CHECK_GL_ERROR
import android.opengl.GLSurfaceView.DEBUG_LOG_GL_CALLS
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import timber.log.Timber

@ExperimentalPagerApi
@Composable
fun GLShader(
    renderer: ShaderRenderer,
    modifier: Modifier = Modifier
) {

    Timber.d("composition surfaceView")

    AndroidView(modifier = modifier,
        factory = {
            ShaderGLSurfaceView(it)
        }) { glSurfaceView ->
        glSurfaceView.debugFlags = DEBUG_CHECK_GL_ERROR or DEBUG_LOG_GL_CALLS
        glSurfaceView.setShaderRenderer(
            renderer
        )
    }
}