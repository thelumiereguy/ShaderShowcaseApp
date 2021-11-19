package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thelumiereguy.shadershowcase.R
import com.thelumiereguy.shadershowcase.core.ui.theme.PrimaryTextColor
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SwipeButtonRow(
    selectedIndex: Int,
    maxItems: Int,
    modifier: Modifier = Modifier,
    onSwiped: (newPageIndex: Int) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = 24.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .padding(
                    start = 16.dp
                )
                .size(36.dp)
        ) {
            if (selectedIndex != 0) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "Slide left",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            border = BorderStroke(1.dp, PrimaryTextColor.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clickable {
                            coroutineScope.launch {
                                onSwiped(selectedIndex - 1)
                            }
                        },
                    tint = PrimaryTextColor
                )
            }
        }


        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(36.dp)
        ) {
            if (selectedIndex != maxItems - 1) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "Slide right",
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            border = BorderStroke(1.dp, PrimaryTextColor.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clickable {
                            coroutineScope.launch {
                                onSwiped(selectedIndex + 1)
                            }
                        }
                        .rotate(
                            180f
                        ),
                    tint = PrimaryTextColor
                )
            }
        }
    }
}