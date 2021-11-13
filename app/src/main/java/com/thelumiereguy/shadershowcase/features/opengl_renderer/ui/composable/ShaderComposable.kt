package com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.renderer.ShaderRenderer
import com.thelumiereguy.shadershowcase.features.opengl_renderer.ui.view.ShaderGLSurfaceView
import timber.log.Timber

@Composable
fun GLShader(
    renderer: ShaderRenderer,
) {

    val context = LocalContext.current

    var view: ShaderGLSurfaceView? = remember {
        ShaderGLSurfaceView(context)
    }

    val lifeCycleState = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(key1 = lifeCycleState) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    view?.onResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    view?.onPause()
                }
                else -> {

                }
            }
        }
        lifeCycleState.addObserver(observer)

        onDispose {
            lifeCycleState.removeObserver(observer)
            view = null
        }
    }

    AndroidView(factory = {
        view ?: ShaderGLSurfaceView(it).also { glSurfaceView ->
            view = glSurfaceView
        }
    }) {
        it.setShaderRenderer(
            renderer
        )
    }
}