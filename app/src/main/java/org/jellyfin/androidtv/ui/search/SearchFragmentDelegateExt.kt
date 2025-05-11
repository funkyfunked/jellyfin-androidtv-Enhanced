package org.jellyfin.androidtv.ui.search

import android.content.Context
import androidx.leanback.widget.ObjectAdapter
import androidx.leanback.widget.ListRow
import org.jellyfin.androidtv.ui.itemhandling.BaseRowItem
import org.jellyfin.androidtv.util.ImageHelper
import org.jellyfin.androidtv.util.ImagePreloader
import org.jellyfin.androidtv.util.apiclient.itemImages
import org.jellyfin.sdk.model.api.ImageType

fun preloadRowImages(rowsAdapter: ObjectAdapter, context: Context, nextN: Int) {
    for (i in 0 until rowsAdapter.size()) {
        val row = rowsAdapter.get(i)
        val adapter = try {
            val listRow = row as? ListRow
            listRow?.adapter as? Iterable<*>
        } catch (_: Exception) { null } ?: continue

        val items = adapter.toList().filterIsInstance<BaseRowItem>()
        val imageHelper = ImageHelper::class.constructors.first().call(org.koin.java.KoinJavaComponent.getKoin().get())
        val urls = items.take(nextN).mapNotNull { item ->
    val baseItem = item.baseItem
    val image = baseItem?.itemImages?.get(org.jellyfin.sdk.model.api.ImageType.PRIMARY)
    image?.let { img -> imageHelper.getImageUrl(img) }
}
        if (urls.isNotEmpty()) {
            ImagePreloader.preloadImages(context, urls)
        }
    }
}

