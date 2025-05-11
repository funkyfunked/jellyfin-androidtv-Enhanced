package org.jellyfin.androidtv.preference

import org.jellyfin.androidtv.constant.HomeSectionType
import org.jellyfin.androidtv.preference.store.DisplayPreferencesStore
import org.jellyfin.preference.enumPreference
import org.jellyfin.preference.intPreference
import org.jellyfin.preference.booleanPreference
import org.jellyfin.sdk.api.client.ApiClient

class UserSettingPreferences(
	api: ApiClient,
) : DisplayPreferencesStore(
	displayPreferencesId = "usersettings",
	api = api,
	app = "emby",
) {
	    val showComedyRow = booleanPreference("showComedyRow", true)
    val showRomanceRow = booleanPreference("showRomanceRow", true)
    val showAnimeRow = booleanPreference("showAnimeRow", true)
    val showAnimationRow = booleanPreference("showAnimationRow", true)
    val showActionRow = booleanPreference("showActionRow", true)
    val showActionAdventureRow = booleanPreference("showActionAdventureRow", true)
    val showFavoritesRow = booleanPreference("showFavoritesRow", true)
    val showMyCollectionsRow = booleanPreference("showMyCollectionsRow", true)
    val showSciFiRow = booleanPreference("showSciFiRow", true)
    val showDocumentaryRow = booleanPreference("showDocumentaryRow", true)
    val showDramaRow = booleanPreference("showDramaRow", true)
    val showRealityTvRow = booleanPreference("showRealityTvRow", true)
    val showFamilyRow = booleanPreference("showFamilyRow", true)
    val showHorrorRow = booleanPreference("showHorrorRow", true)
    val showFantasyRow = booleanPreference("showFantasyRow", true)
    val showHistoryRow = booleanPreference("showHistoryRow", true)
    val showMusicRow = booleanPreference("showMusicRow", true)
    val showMysteryRow = booleanPreference("showMysteryRow", true)
    val showRealityRow = booleanPreference("showRealityRow", true)
    val showThrillerRow = booleanPreference("showThrillerRow", true)
    val showWarRow = booleanPreference("showWarRow", true)

    private val defaultGenreOrder = listOf(
        "Comedy",
        "Romance",
        "Anime",
        "Animation",
        "Action",
        "Sci-Fi",
        "Documentary",
        "Drama",
        "Family",
        "Horror",
        "Fantasy",
        "History",
        "Music",
        "Mystery",
        "Reality",
        "Thriller",
        "War"
    )
    private val genreRowOrderKey = "genreRowOrder"

    @JvmField
    val skipBackLength = intPreference("skipBackLength", 10_000)
    @JvmField
    val skipForwardLength = intPreference("skipForwardLength", 30_000)

    val homesection0 = enumPreference("homesection0", HomeSectionType.LIBRARY_TILES_SMALL)
    val homesection1 = enumPreference("homesection1", HomeSectionType.RESUME)
    val homesection2 = enumPreference("homesection2", HomeSectionType.RESUME_AUDIO)
    val homesection3 = enumPreference("homesection3", HomeSectionType.RESUME_BOOK)
    val homesection4 = enumPreference("homesection4", HomeSectionType.LIVE_TV)
    val homesection5 = enumPreference("homesection5", HomeSectionType.NEXT_UP)
    val homesection6 = enumPreference("homesection6", HomeSectionType.LATEST_MEDIA)
    val homesection7 = enumPreference("homesection7", HomeSectionType.NONE)
    val homesection8 = enumPreference("homesection8", HomeSectionType.NONE)
    val homesection9 = enumPreference("homesection9", HomeSectionType.NONE)


    fun getGenreRowOrder(): List<String> {
        val raw = getString(genreRowOrderKey, defaultGenreOrder.joinToString(","))
        return raw.split(',').map { it.trim() }.filter { it.isNotEmpty() }
    }
    fun setGenreRowOrder(order: List<String>) {
        setString(genreRowOrderKey, order.joinToString(","))
    }
}
