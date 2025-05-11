package org.jellyfin.androidtv.ui.preference.screen

import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.constant.HomeSectionType
import org.jellyfin.androidtv.preference.UserSettingPreferences
import org.jellyfin.androidtv.ui.preference.dsl.OptionsFragment
import org.jellyfin.androidtv.ui.preference.dsl.optionsScreen
import org.jellyfin.androidtv.ui.preference.dsl.enum
import org.jellyfin.androidtv.ui.preference.dsl.checkbox
import org.jellyfin.androidtv.ui.preference.dsl.checkbox
import org.jellyfin.preference.store.PreferenceStore
import org.koin.android.ext.android.inject

class HomePreferencesScreen : OptionsFragment() {
	private val userSettingPreferences: UserSettingPreferences by inject()

	override val screen by optionsScreen {
		setTitle(R.string.home_prefs)

		category {
			setTitle(R.string.home_sections)

			listOf(
				userSettingPreferences.homesection0,
				userSettingPreferences.homesection1,
				userSettingPreferences.homesection2,
				userSettingPreferences.homesection3,
				userSettingPreferences.homesection4,
				userSettingPreferences.homesection5,
				userSettingPreferences.homesection6,
				userSettingPreferences.homesection7,
				userSettingPreferences.homesection8,
				userSettingPreferences.homesection9
			).forEachIndexed { index, sectionPref ->
				enum<HomeSectionType> {
					title = getString(R.string.home_section_i, index + 1)
					bind(userSettingPreferences, sectionPref)
				}
			}
		}

		category {
    setTitle(R.string.genre_rows)

    // Restored: Genre row checkboxes for toggling visibility on the home menu
    checkbox {
        title = getString(R.string.genre_row_favorites)
        bind(userSettingPreferences, userSettingPreferences.showFavoritesRow)
    }
    checkbox {
        title = getString(R.string.show_my_collections_row)
        bind(userSettingPreferences, userSettingPreferences.showMyCollectionsRow)
    }
    checkbox {
        title = getString(R.string.show_sci_fi_row)
        bind(userSettingPreferences, userSettingPreferences.showSciFiRow)
    }
    checkbox {
        title = getString(R.string.show_romance_row)
        bind(userSettingPreferences, userSettingPreferences.showRomanceRow)
    }
    checkbox {
        title = getString(R.string.show_anime_row)
        bind(userSettingPreferences, userSettingPreferences.showAnimeRow)
    }
    checkbox {
        title = getString(R.string.show_action_row)
        bind(userSettingPreferences, userSettingPreferences.showActionRow)
    }
    checkbox {
        title = getString(R.string.genre_row_action_adventure)
        bind(userSettingPreferences, userSettingPreferences.showActionAdventureRow)
    }
    checkbox {
        title = getString(R.string.show_comedy_row)
        bind(userSettingPreferences, userSettingPreferences.showComedyRow)
    }
    checkbox {
        title = getString(R.string.show_documentary_row)
        bind(userSettingPreferences, userSettingPreferences.showDocumentaryRow)
    }
    checkbox {
        title = getString(R.string.show_reality_tv_row)
        bind(userSettingPreferences, userSettingPreferences.showRealityTvRow)
    }
    checkbox {
        title = getString(R.string.show_family_row)
        bind(userSettingPreferences, userSettingPreferences.showFamilyRow)
    }
    checkbox {
        title = getString(R.string.show_horror_row)
        bind(userSettingPreferences, userSettingPreferences.showHorrorRow)
    }
    checkbox {
        title = getString(R.string.show_fantasy_row)
        bind(userSettingPreferences, userSettingPreferences.showFantasyRow)
    }
    checkbox {
        title = getString(R.string.show_history_row)
        bind(userSettingPreferences, userSettingPreferences.showHistoryRow)
    }
    checkbox {
        title = getString(R.string.show_music_row)
        bind(userSettingPreferences, userSettingPreferences.showMusicRow)
    }
    checkbox {
        title = getString(R.string.show_mystery_row)
        bind(userSettingPreferences, userSettingPreferences.showMysteryRow)
    }
    checkbox {
        title = getString(R.string.show_thriller_row)
        bind(userSettingPreferences, userSettingPreferences.showThrillerRow)
    }
    checkbox {
        title = getString(R.string.show_war_row)
        bind(userSettingPreferences, userSettingPreferences.showWarRow)
    }
}

	}

	override val stores: Array<PreferenceStore<*, *>>
		get() = arrayOf(userSettingPreferences)
}
