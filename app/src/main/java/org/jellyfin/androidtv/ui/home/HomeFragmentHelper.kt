package org.jellyfin.androidtv.ui.home

import android.content.Context
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.auth.repository.UserRepository
import org.jellyfin.androidtv.constant.ChangeTriggerType
import org.jellyfin.androidtv.data.repository.ItemRepository
import org.jellyfin.androidtv.ui.browsing.BrowseRowDef
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemKind
import org.jellyfin.sdk.model.api.MediaType
import org.jellyfin.sdk.model.api.request.GetNextUpRequest
import org.jellyfin.sdk.model.api.request.GetRecommendedProgramsRequest
import org.jellyfin.sdk.model.api.request.GetRecordingsRequest
import org.jellyfin.sdk.model.api.request.GetResumeItemsRequest

import org.jellyfin.sdk.model.api.ItemSortBy

class HomeFragmentHelper(
    private val context: Context,
    private val userRepository: UserRepository
) {
    fun loadMyCollectionsRow(): HomeFragmentRow {
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            fields = ItemRepository.itemFields,
            includeItemTypes = setOf(BaseItemKind.BOX_SET),
            recursive = true,
            imageTypeLimit = 1,
            limit = 20,
            sortBy = setOf(ItemSortBy.SORT_NAME),
        )
        return HomeFragmentBrowseRowDefRow(
            org.jellyfin.androidtv.ui.browsing.BrowseRowDef(context.getString(R.string.lbl_my_collections), query, 20)
        )
    }

    fun loadComedyRow(): HomeFragmentRow {
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            genres = setOf("Comedy"),
            sortBy = setOf(ItemSortBy.SORT_NAME),
            limit = 20,
            fields = ItemRepository.itemFields,
            recursive = true,
            excludeItemTypes = setOf(BaseItemKind.EPISODE)
        )
        return HomeFragmentBrowseRowDefRow(org.jellyfin.androidtv.ui.browsing.BrowseRowDef("Comedy", query, 20))
    }

    fun loadRomanceRow(): HomeFragmentRow {
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            genres = setOf("Romance"),
            sortBy = setOf(ItemSortBy.SORT_NAME),
            limit = 20,
            fields = ItemRepository.itemFields,
            recursive = true,
            excludeItemTypes = setOf(BaseItemKind.EPISODE)
        )
        return HomeFragmentBrowseRowDefRow(org.jellyfin.androidtv.ui.browsing.BrowseRowDef("Romance", query, 20))
    }

    fun loadAnimeRow(): HomeFragmentRow = genreRow("Anime")
    fun loadAnimationRow(): HomeFragmentRow = genreRow("Animation")
    fun loadActionRow(): HomeFragmentRow = genreRow("Action")
    fun loadActionAdventureRow(): HomeFragmentRow = genreRow("Action & Adventure")
    fun loadFavoritesRow(): HomeFragmentRow {
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            isFavorite = true,
            sortBy = setOf(ItemSortBy.DATE_CREATED),
            limit = 20,
            fields = ItemRepository.itemFields,
            recursive = true,
            excludeItemTypes = setOf(BaseItemKind.EPISODE)
        )
        return HomeFragmentBrowseRowDefRow(org.jellyfin.androidtv.ui.browsing.BrowseRowDef(context.getString(R.string.lbl_favorites), query, 20))
    }

    fun loadSciFiRow(): HomeFragmentRow = genreRow("Sci-Fi")
    fun loadDocumentaryRow(): HomeFragmentRow = genreRow("Documentary")
    fun loadDramaRow(): HomeFragmentRow = genreRow("Drama")
    fun loadRealityRow(): HomeFragmentRow = genreRow("Reality")
    fun loadFamilyRow(): HomeFragmentRow = genreRow("Family")
    fun loadHorrorRow(): HomeFragmentRow = genreRow("Horror")
    fun loadFantasyRow(): HomeFragmentRow = genreRow("Fantasy")
    fun loadHistoryRow(): HomeFragmentRow = genreRow("History")
    fun loadMusicRow(): HomeFragmentRow {
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            includeItemTypes = setOf(BaseItemKind.PLAYLIST),
            sortBy = setOf(ItemSortBy.SORT_NAME),
            limit = 50,
            fields = ItemRepository.itemFields,
            recursive = true
        )
        return HomeFragmentBrowseRowDefRow(org.jellyfin.androidtv.ui.browsing.BrowseRowDef("Playlists", query, 50))
    }
    fun loadMysteryRow(): HomeFragmentRow = genreRow("Mystery")
    fun loadThrillerRow(): HomeFragmentRow = genreRow("Thriller")
    fun loadWarRow(): HomeFragmentRow = genreRow("War")

    private fun genreRow(name: String): HomeFragmentRow {
        val genreVariants = when (name) {
            "Reality" -> listOf(
                "Reality",
                " Reality",
                "Reality ",
                " Reality ",
                "Reality-TV",
                "Reality TV",
                "Reality Shows"
            )
            "Sci-Fi" -> listOf(
                "Sci-Fi",
                " Sci-Fi",
                "Sci-Fi ",
                " Sci-Fi ",
                "SciFi",
                " SciFi",
                "SciFi ",
                " SciFi ",
                "Science Fiction",
                " Science Fiction",
                "Science Fiction ",
                " Science Fiction "
            )
            else -> listOf(
                name,
                " $name",
                "$name ",
                " $name ",
                "$name-TV",
                "$name TV",
                "$name Shows"
            )
        }
        val query = org.jellyfin.sdk.model.api.request.GetItemsRequest(
            genres = genreVariants.toSet(),
            sortBy = setOf(ItemSortBy.SORT_NAME),
            limit = 50,  // Increased limit to catch more items
            fields = ItemRepository.itemFields,
            recursive = true,
            excludeItemTypes = setOf(BaseItemKind.EPISODE)
        )
        timber.log.Timber.e("Genre Row Debug: "
            + "Name: $name, "
            + "Genres Searched: $genreVariants, "
            + "Limit: ${query.limit}, "
            + "Recursive: ${query.recursive}, "
            + "Excluded Types: ${query.excludeItemTypes}"
        )
        return HomeFragmentBrowseRowDefRow(org.jellyfin.androidtv.ui.browsing.BrowseRowDef(name, query, 50))
    }

    fun loadRecentlyAdded(userViews: Collection<BaseItemDto>): HomeFragmentRow {
        return HomeFragmentLatestRow(userRepository, userViews)
    }

    fun loadResume(title: String, includeMediaTypes: Collection<MediaType>): HomeFragmentRow {
        val query = GetResumeItemsRequest(
            limit = ITEM_LIMIT_RESUME,
            fields = ItemRepository.itemFields,
            imageTypeLimit = 1,
            enableTotalRecordCount = false,
            mediaTypes = includeMediaTypes,
            excludeItemTypes = setOf(BaseItemKind.AUDIO_BOOK),
        )

        return HomeFragmentBrowseRowDefRow(BrowseRowDef(title, query, 0, false, true, arrayOf(ChangeTriggerType.TvPlayback, ChangeTriggerType.MoviePlayback)))
    }

    fun loadResumeVideo(): HomeFragmentRow {
        return loadResume(context.getString(R.string.lbl_continue_watching), listOf(MediaType.VIDEO))
    }

    fun loadResumeAudio(): HomeFragmentRow {
        return loadResume(context.getString(R.string.continue_listening), listOf(MediaType.AUDIO))
    }

    fun loadLatestLiveTvRecordings(): HomeFragmentRow {
        val query = GetRecordingsRequest(
            fields = ItemRepository.itemFields,
            enableImages = true,
            limit = ITEM_LIMIT_RECORDINGS
        )

        return HomeFragmentBrowseRowDefRow(BrowseRowDef(context.getString(R.string.lbl_recordings), query))
    }

    fun loadNextUp(): HomeFragmentRow {
        val query = GetNextUpRequest(
            imageTypeLimit = 1,
            limit = ITEM_LIMIT_NEXT_UP,
            enableResumable = false,
            fields = ItemRepository.itemFields
        )

        return HomeFragmentBrowseRowDefRow(BrowseRowDef(context.getString(R.string.lbl_next_up), query, arrayOf(ChangeTriggerType.TvPlayback)))
    }

    fun loadOnNow(): HomeFragmentRow {
        val query = GetRecommendedProgramsRequest(
            isAiring = true,
            fields = ItemRepository.itemFields,
            imageTypeLimit = 1,
            enableTotalRecordCount = false,
            limit = ITEM_LIMIT_ON_NOW
        )

        return HomeFragmentBrowseRowDefRow(BrowseRowDef(context.getString(R.string.lbl_on_now), query))
    }

    companion object {
        // Maximum amount of items loaded for a row
        private const val ITEM_LIMIT_RESUME = 50
        private const val ITEM_LIMIT_RECORDINGS = 40
        private const val ITEM_LIMIT_NEXT_UP = 50
        private const val ITEM_LIMIT_ON_NOW = 20
    }
}
