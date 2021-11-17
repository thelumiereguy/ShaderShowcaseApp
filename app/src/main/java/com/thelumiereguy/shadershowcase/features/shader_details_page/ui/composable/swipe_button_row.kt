package com.thelumiereguy.shadershowcase.features.shader_details_page.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.thelumiereguy.shadershowcase.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun SwipeButtonRow(
    selectedIndex: Int,
    maxItems: Int,
    modifier: Modifier = Modifier,
    onSwiped:  (newPageIndex: Int) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(36.dp)
        ) {
            if (selectedIndex != 0) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "Slide left",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            coroutineScope.launch {
                                onSwiped(selectedIndex - 1)
                            }
                        }
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
                        .clickable {
                            coroutineScope.launch {
                                onSwiped(selectedIndex + 1)
                            }
                        }
                        .rotate(
                            180f
                        )
                )
            }
        }
    }
}