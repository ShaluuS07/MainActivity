package com.example.mainactivity.mvvm.you.view

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PeopleOutline
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mainactivity.R
import com.example.mainactivity.design.theme.MatrimonyAccent
import com.example.mainactivity.design.theme.MatrimonyPrimary
import com.example.mainactivity.design.theme.MatrimonyPrimaryDark
import com.example.mainactivity.design.theme.MatrimonyPrimaryLight
import kotlinx.coroutines.launch

private data class DemoProfile(
    val name: String,
    val matrimonyId: String,
    val photoUrl: String,
    val completion: Float,
    val profileViews: Int,
    val interests: Int,
    val contacts: Int,
)

private val demoProfile = DemoProfile(
    name = "Arjun Raj",
    matrimonyId = "MC8472915",
    photoUrl = "https://images.pexels.com/photos/3771317/pexels-photo-3771317.jpeg?auto=compress&cs=tinysrgb&w=400&h=400&fit=crop",
    completion = 0.78f,
    profileViews = 128,
    interests = 24,
    contacts = 3,
)

@Composable
fun YouScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val comingSoon: () -> Unit = {
        scope.launch {
            snackbarHostState.showSnackbar(context.getString(R.string.you_coming_soon))
        }
    }
    val versionName = remember {
        try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
        } catch (_: Exception) {
            "1.0"
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
            item {
                YouHeroHeader(
                    profile = demoProfile,
                    onEditProfile = comingSoon,
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
            }
            item {
                ProfileCompletionCard(
                    completion = demoProfile.completion,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
            }
            item {
                StatsRow(
                    profile = demoProfile,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
            item {
                Spacer(Modifier.height(20.dp))
            }
            item {
                SectionLabel(stringResource(R.string.you_section_account))
            }
            item {
                MenuCard {
                    YouListItem(
                        icon = { Icon(Icons.Default.PersonOutline, contentDescription = null) },
                        title = stringResource(R.string.you_menu_my_profile),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
                        title = stringResource(R.string.you_menu_partner_preferences),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.Security, contentDescription = null) },
                        title = stringResource(R.string.you_menu_privacy),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.NotificationsNone, contentDescription = null) },
                        title = stringResource(R.string.you_menu_notifications),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        title = stringResource(R.string.you_menu_account_settings),
                        onClick = comingSoon,
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                SectionLabel(stringResource(R.string.you_section_support))
            }
            item {
                MenuCard {
                    YouListItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.HelpOutline, contentDescription = null) },
                        title = stringResource(R.string.you_menu_help),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.PhoneInTalk, contentDescription = null) },
                        title = stringResource(R.string.you_menu_contact),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.PeopleOutline, contentDescription = null) },
                        title = stringResource(R.string.you_menu_safety),
                        onClick = comingSoon,
                    )
                    HorizontalDivider()
                    YouListItem(
                        icon = { Icon(Icons.Default.Description, contentDescription = null) },
                        title = stringResource(R.string.you_menu_terms),
                        onClick = comingSoon,
                    )
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable { comingSoon() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f),
                    ),
                ) {
                    ListItem(
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.you_menu_logout),
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.AutoMirrored.Filled.Logout,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                            )
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }
            item {
                Text(
                    text = stringResource(R.string.you_version_line, versionName),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
    )
}

@Composable
private fun MenuCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column { content() }
    }
}

@Composable
private fun YouListItem(
    icon: @Composable () -> Unit,
    title: String,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = {
            Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        leadingContent = icon,
        trailingContent = {
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        modifier = Modifier.clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Composable
private fun YouHeroHeader(
    profile: DemoProfile,
    onEditProfile: () -> Unit,
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            MatrimonyPrimaryDark,
            MatrimonyPrimary,
            MatrimonyPrimaryLight.copy(alpha = 0.95f),
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 28.dp),
        ) {
            Text(
                text = stringResource(R.string.you_hero_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.you_hero_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 4.dp),
            )
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AsyncImage(
                    model = profile.photoUrl,
                    contentDescription = stringResource(R.string.you_cd_profile_photo),
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)),
                    contentScale = ContentScale.Crop,
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = stringResource(R.string.you_matrimony_id, profile.matrimonyId),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 4.dp),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp),
                    ) {
                        MembershipChip(stringResource(R.string.you_badge_verified), accent = false)
                        MembershipChip(stringResource(R.string.you_badge_free), accent = true)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = onEditProfile,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    stringResource(R.string.you_edit_profile),
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun MembershipChip(text: String, accent: Boolean) {
    val bg = if (accent) {
        MatrimonyAccent.copy(alpha = 0.35f)
    } else {
        Color.White.copy(alpha = 0.22f)
    }
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Medium,
                        color = Color.White,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

@Composable
private fun ProfileCompletionCard(
    completion: Float,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.you_profile_completion),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = stringResource(R.string.you_percent_format, (completion * 100).toInt()),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MatrimonyPrimary,
                )
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { completion },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MatrimonyPrimary,
                trackColor = MatrimonyPrimaryLight.copy(alpha = 0.5f),
            )
            Text(
                text = stringResource(R.string.you_profile_completion_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun StatsRow(
    profile: DemoProfile,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StatTile(
            value = profile.profileViews.toString(),
            label = stringResource(R.string.you_stat_views),
            icon = Icons.Default.Visibility,
            modifier = Modifier.weight(1f),
        )
        StatTile(
            value = profile.interests.toString(),
            label = stringResource(R.string.you_stat_interests),
            icon = Icons.Default.FavoriteBorder,
            modifier = Modifier.weight(1f),
        )
        StatTile(
            value = profile.contacts.toString(),
            label = stringResource(R.string.you_stat_contacts),
            icon = Icons.Default.PeopleOutline,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun StatTile(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MatrimonyPrimary,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
        }
    }
}
