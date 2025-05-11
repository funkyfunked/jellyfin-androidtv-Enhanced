package org.jellyfin.androidtv.util

import android.content.Context
import coil3.ImageLoader
import coil3.request.ImageRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ImagePreloader : KoinComponent {
    private val imageLoader by inject<ImageLoader>()

    fun preloadImages(context: Context, urls: List<String>) {
        urls.forEach { url ->
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            imageLoader.enqueue(request)
        }
    }
}
