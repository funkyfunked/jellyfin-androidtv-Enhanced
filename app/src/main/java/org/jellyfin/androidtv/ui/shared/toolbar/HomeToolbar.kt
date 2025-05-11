package org.jellyfin.androidtv.ui.shared.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.ui.base.Icon
import org.jellyfin.androidtv.ui.base.Text
import org.jellyfin.androidtv.ui.base.button.IconButton
import org.jellyfin.androidtv.ui.base.button.IconButtonDefaults

@Composable
fun HomeToolbar(
    openSearch: () -> Unit,
    openSettings: () -> Unit,
    switchUsers: () -> Unit,
) {
    Toolbar {
        Row(horizontalArrangement = Arrangement.spacedBy(23.dp)) {
            // Search Icon + Label
            IconButton(onClick = openSearch, modifier = Modifier.width(105.dp).height(37.dp), colors = IconButtonDefaults.colors(containerColor = Color.Transparent)) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = stringResource(R.string.lbl_search),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    org.jellyfin.androidtv.ui.base.Text(
                        text = stringResource(R.string.lbl_search)
                    )
                }
            }
            // Settings Icon + Label
            IconButton(onClick = openSettings, modifier = Modifier.width(105.dp).height(37.dp), colors = IconButtonDefaults.colors(containerColor = Color.Transparent)) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_settings),
                        contentDescription = stringResource(R.string.lbl_settings),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    org.jellyfin.androidtv.ui.base.Text(
                        text = stringResource(R.string.lbl_settings)
                    )
                }
            }
            // User Icon + Label
            IconButton(onClick = switchUsers, modifier = Modifier.width(105.dp).height(37.dp), colors = IconButtonDefaults.colors(containerColor = Color.Transparent)) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_users),
                        contentDescription = "Users",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    org.jellyfin.androidtv.ui.base.Text(
                        text = "Users"
                    )
                }
            }
        }
        Spacer(Modifier.width(20.dp))
        ToolbarButtons {
            // Additional toolbar buttons can go here if needed
        }
    }
}
