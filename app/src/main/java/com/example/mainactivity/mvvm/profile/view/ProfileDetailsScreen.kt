package com.example.mainactivity.mvvm.profile.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mainactivity.R
import com.example.mainactivity.design.theme.MatrimonyPrimary
import com.example.mainactivity.design.theme.MatrimonyPrimaryDark
import com.example.mainactivity.design.theme.MatrimonyPrimaryLight
import com.example.mainactivity.mvvm.profile.model.ProfileAstrologyUi
import com.example.mainactivity.mvvm.profile.model.ProfileUi

private val ProfileSheetPeekHeight = 220.dp
private val SheetCornerRadius = 24.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileDetailsScreen(
    profile: ProfileUi?,
    onBack: () -> Unit,
) {
    if (profile == null) {
        ProfileDetailsEmptyState(onBack = onBack)
        return
    }

    val photoUrls = profile.photoUrls
    val pagerState = rememberPagerState(pageCount = { photoUrls.size })
    val context = LocalContext.current

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState,
    )

    // Drag handle lives inside the same rounded Surface as the sheet so corner radius stays
    // consistent in peek vs expanded (scaffold sheet layer alone can look square until expanded).
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.onSurface,
        sheetPeekHeight = ProfileSheetPeekHeight,
        sheetSwipeEnabled = true,
        sheetDragHandle = {},
        sheetShape = RoundedCornerShape(topStart = SheetCornerRadius, topEnd = SheetCornerRadius),
        sheetContainerColor = Color.Transparent,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 0.dp,
        sheetTonalElevation = 0.dp,
        sheetContent = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                tonalElevation = 0.dp,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(topStart = SheetCornerRadius, topEnd = SheetCornerRadius),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        BottomSheetDefaults.DragHandle()
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .verticalScroll(rememberScrollState())
                            .padding(start = 20.dp, top = 4.dp, end = 20.dp, bottom = 28.dp),
                    ) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ProfileFactChip("${profile.age} yrs")
                        ProfileFactChip(profile.height)
                        ProfileFactChip(profile.language)
                        ProfileFactChip(profile.religionCommunity)
                        ProfileFactChip(profile.city)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${profile.education} · ${profile.profession}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${profile.city}, ${profile.stateCountry}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ProfileSectionCard(
                        icon = Icons.Default.Favorite,
                        title = "Partner preference",
                        iconTint = MaterialTheme.colorScheme.primary,
                    ) {
                        profile.sheetContent.partnerPoints.forEachIndexed { index, point ->
                            if (index > 0) Spacer(modifier = Modifier.height(10.dp))
                            BulletPoint(text = point)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileSectionCard(
                        icon = Icons.Default.Groups,
                        title = "Family",
                        iconTint = MaterialTheme.colorScheme.tertiary,
                    ) {
                        profile.sheetContent.familyPoints.forEachIndexed { index, line ->
                            if (index > 0) Spacer(modifier = Modifier.height(10.dp))
                            BulletPoint(text = line)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileSectionCard(
                        icon = Icons.Default.AutoAwesome,
                        title = "Astrology",
                        iconTint = Color(0xFF6A4C93),
                    ) {
                        AstrologyGrid(profile.sheetContent.astrology)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ProfileSectionCard(
                        icon = Icons.Default.Interests,
                        title = "Hobbies & interests",
                        iconTint = MaterialTheme.colorScheme.secondary,
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            profile.sheetContent.hobbyTags.forEach { tag ->
                                SuggestionPill(text = tag)
                            }
                        }
                    }
                    }
                }
            }
        },
        content = { paddingValues: PaddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(photoUrls[page])
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE8ECF2)),
                        contentScale = ContentScale.Crop,
                    )
                }

                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White,
                        )
                    }
                    Text(
                        text = profile.profileIdCode,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                // Scaffold content is already inset above the sheet peek; do not add peek height again
                // or indicators sit mid-image. Small margin only, flush to image bottom.
                val overlayBottomMargin = 16.dp
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = overlayBottomMargin),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    repeat(photoUrls.size) { index ->
                        val active = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .size(if (active) 8.dp else 6.dp)
                                .clip(CircleShape)
                                .background(if (active) Color(0xFF2196F3) else Color.White.copy(alpha = 0.6f)),
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = overlayBottomMargin, end = 16.dp)
                        .background(Color(0x99000000), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    val current = pagerState.currentPage + 1
                    Text(
                        text = stringResource(R.string.photo_slash_total, current, profile.photoCount),
                        color = Color.White,
                        fontSize = 13.sp,
                    )
                }
            }
        },
    )
}

@Composable
private fun ProfileFactChip(text: String) {
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        border = null,
    )
}

@Composable
private fun ProfileSectionCard(
    icon: ImageVector,
    title: String,
    iconTint: Color,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconTint.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun AstrologyGrid(astrology: ProfileAstrologyUi) {
    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
        AstroRow(label = "Rashi", value = astrology.rashi)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )
        AstroRow(label = "Nakshatra", value = astrology.nakshatra)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )
        AstroRow(label = "Gothra", value = astrology.gothra)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )
        AstroRow(label = "Dosham", value = astrology.doshamNote)
    }
}

@Composable
private fun AstroRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(96.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        )
    }
}

@Composable
private fun SuggestionPill(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp),
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
    )
}

@Composable
private fun ProfileDetailsEmptyState(onBack: () -> Unit) {
    val infinite = rememberInfiniteTransition(label = "empty")
    val drift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "drift",
    )
    val heartScale by infinite.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "heart",
    )
    val textAlpha by infinite.animateFloat(
        initialValue = 0.88f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "textAlpha",
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val wPx = constraints.maxWidth.toFloat().coerceAtLeast(1f)
            val hPx = constraints.maxHeight.toFloat().coerceAtLeast(1f)
            val ox = drift * 0.12f * wPx
            val oy = drift * 0.08f * hPx
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MatrimonyPrimaryLight,
                                MatrimonyPrimary,
                                MatrimonyPrimaryDark,
                            ),
                            start = Offset(-ox, -oy),
                            end = Offset(wPx * 1.08f + ox, hPx * 1.08f + oy),
                        ),
                    ),
            )
        }

        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.95f),
                modifier = Modifier
                    .size(56.dp)
                    .graphicsLayer {
                        scaleX = heartScale
                        scaleY = heartScale
                    },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.no_profiles_left),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = textAlpha),
            )
        }
    }
}
