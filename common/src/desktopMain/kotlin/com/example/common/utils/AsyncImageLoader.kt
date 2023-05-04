package com.example.common.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import java.io.IOException
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
actual fun AsyncImageLoader(
    imageUrl: String?,
    loadingPlaceHolder: @Composable BoxScope.() -> Unit,
    errorPlaceHolder: @Composable BoxScope.() -> Unit,
    contentDescription: String?,
    modifier: Modifier,
    alignment: Alignment,
    contentScale: ContentScale,
    alpha: Float,
    coloFilter: ColorFilter?,
    filterQuality: FilterQuality
) {
    Box(modifier = modifier) {
        if (!imageUrl.isNullOrEmpty()) {
            val imageState by rememberImageState(imageUrl)
            when (val state = imageState) {
                ImageState.Error -> errorPlaceHolder()
                ImageState.Loading -> loadingPlaceHolder()
                is ImageState.Success -> {
                    Image(
                        bitmap = state.bitmap,
                        contentDescription = contentDescription,
                        modifier = modifier,
                        alignment = alignment,
                        contentScale = contentScale,
                        alpha = alpha,
                        colorFilter = coloFilter,
                        filterQuality = filterQuality
                    )
                }
            }
        } else {
            errorPlaceHolder()
        }
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                println("Error loading image: ${e.localizedMessage}")
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }

}

fun loadImageBitmap(url: String?): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)


@Composable
fun rememberImageState(
    imageUrl: String
) = produceState<ImageState>(initialValue = ImageState.Loading) {
    runCatching {
        loadImageBitmap(imageUrl)
    }.onSuccess {
        value = ImageState.Success(it)
    }.onFailure {
        value = ImageState.Error
    }
}

sealed class ImageState {
    object Loading : ImageState()
    object Error : ImageState()
    data class Success(val bitmap: ImageBitmap) : ImageState()
}