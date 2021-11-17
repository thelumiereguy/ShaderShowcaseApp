package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable

import android.opengl.GLSurfaceView.DEBUG_CHECK_GL_ERROR
import android.opengl.GLSurfaceView.DEBUG_LOG_GL_CALLS
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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

    var view: ShaderGLSurfaceView? = remember {
        null
    }

    val lifeCycleState = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(key1 = lifeCycleState) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    view?.onResume()
                    renderer.onResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    view?.onPause()
                    renderer.onPause()
                }
                else -> {
                }
            }
        }
        lifeCycleState.addObserver(observer)

        onDispose {
            Timber.d("View Disposed ${view.hashCode()}")
            lifeCycleState.removeObserver(observer)
            view?.onPause()
            view = null
        }
    }

    AndroidView(modifier = modifier,
        factory = {
            ShaderGLSurfaceView(it)
        }) { glSurfaceView ->
        view = glSurfaceView
        glSurfaceView.debugFlags = DEBUG_CHECK_GL_ERROR or DEBUG_LOG_GL_CALLS
        glSurfaceView.setShaderRenderer(
            renderer
        )
    }
}