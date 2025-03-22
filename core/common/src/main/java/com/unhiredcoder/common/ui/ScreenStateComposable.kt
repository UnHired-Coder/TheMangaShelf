package com.unhiredcoder.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.unhiredcoder.common.data.Resource
import kotlinx.coroutines.flow.StateFlow


@Composable
fun <T> ScreenStateComposable(
    modifier: Modifier = Modifier,
    resourceFlow: StateFlow<Resource<T>>,
    onSuccessComposable: @Composable BoxScope.(data: T) -> Unit,
    onFailureComposable: @Composable (BoxScope.() -> Unit)? = null,
    onOnLoadingComposable: @Composable (BoxScope.() -> Unit)? = null
) {
    val resource by resourceFlow.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                resource.data?.let { data ->
                    onSuccessComposable(data)
                }

                // show loader
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxSize(),
                    visible = resource is Resource.Loading
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        onOnLoadingComposable?.invoke(this) ?: kotlin.run {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(20.dp),
                                color = Color.Gray
                            )
                        }
                    }
                }

                if (resource is Resource.Failure) {
                    onFailureComposable?.invoke(this) ?: kotlin.run {
                        LaunchedEffect(Unit) {
                            //Show Error
                            snackBarHostState.showSnackbar(message = (resource as Resource.Failure<T>).errorMessage.message.toString())
                        }
                    }
                }
            }
        })
}
