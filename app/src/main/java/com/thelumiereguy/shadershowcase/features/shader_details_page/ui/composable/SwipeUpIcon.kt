package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import com.thelumiereguy.shadershowcase.features.shader_details_page.ui.helper.map
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun SwipeIcon(
    showMenu: State<Boolean>,
    swipeableState: SwipeableState<Int>,
    sizePx: Float
) {
    val repeatingAnimation = rememberInfiniteTransition()

    val offset by repeatingAnimation.animateFloat(
        0f,
        -20f,
        infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    Icon(
        painter = painterResource(id = R.drawable.ic_double_down),
        contentDescription = "Scroll to show or hide options",
        tint = PrimaryTextColor,
        modifier = Modifier
            .size(16.dp)
            .offset {
                IntOffset(
                    0,
                    if (showMenu.value) {
                        0
                    } else {
                        offset.absoluteValue.roundToInt()
                    }
                )
            }
            .rotate(
                map(
                    swipeableState.offset.value,
                    sizePx,
                    0f,
                    180f,
                    0f
                ),
            )
    )
}