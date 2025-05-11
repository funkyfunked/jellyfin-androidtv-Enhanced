package org.jellyfin.androidtv.ui.preference.screen

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.preference.GenreRowPreferences
import org.jellyfin.androidtv.ui.preference.dsl.OptionsFragment
import org.jellyfin.androidtv.ui.preference.dsl.list
import org.jellyfin.androidtv.ui.preference.dsl.optionsScreen
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.genresApi
import org.jellyfin.sdk.model.api.BaseItemKind
import org.koin.android.ext.android.inject

// GenreRowPreferencesScreen implementation is reverted. Please restore previous logic or leave empty if not needed.

class GenreRowPreferencesScreen : OptionsFragment() {
    private val apiClient: ApiClient by inject()
    private val genreRowPreferences: GenreRowPreferences by inject()

    private var movieGenres: Map<String, String> = emptyMap()
    private var tvGenres: Map<String, String> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val fetchedMovieGenres = fetchGenres(BaseItemKind.MOVIE)
            val fetchedTvGenres = fetchGenres(BaseItemKind.SERIES)
            // Prepend static genres
            movieGenres = linkedMapOf(
                "My Collection" to "My Collection",
                "Action & Adventure" to "Action & Adventure"
            ) + fetchedMovieGenres
            tvGenres = linkedMapOf(
                "My Collection" to "My Collection",
                "Favorites (from TV Shows and Movies)" to "Favorites (from TV Shows and Movies)"
            ) + fetchedTvGenres
            requireActivity().runOnUiThread { requireActivity().recreate() }
        }
    }

    private suspend fun fetchGenres(kind: BaseItemKind): Map<String, String> = withContext(Dispatchers.IO) {
        val response = apiClient.genresApi.getGenres(
            sortBy = setOf(org.jellyfin.sdk.model.api.ItemSortBy.SORT_NAME),
            includeItemTypes = setOf(kind)
        ).content
        response.items.mapNotNull { it.name }.associateWith { it }
    }

    override val screen by optionsScreen {
        setTitle(getString(R.string.genre_row_prefs))
        if (movieGenres.isEmpty() || tvGenres.isEmpty()) {
            category {
                setTitle(getString(R.string.genre_row_loading))
            }
        } else {
            category {
                setTitle(getString(R.string.genre_row_movie))
                list {
                    title = getString(R.string.genre_row_movie)
                    entries = movieGenres
                    bind(genreRowPreferences, GenreRowPreferences.homeGenreMovie)
                }
            }
            category {
                setTitle(getString(R.string.genre_row_tvshow))
                list {
                    title = getString(R.string.genre_row_tvshow)
                    entries = tvGenres
                    bind(genreRowPreferences, GenreRowPreferences.homeGenreTvShow)
                }
            }
        }
    }
}



