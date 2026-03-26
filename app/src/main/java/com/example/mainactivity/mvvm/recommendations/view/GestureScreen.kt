package com.example.mainactivity.mvvm.recommendations.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.mainactivity.R
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mainactivity.mvvm.recommendations.viewmodel.GestureViewModel
import com.example.mainactivity.mvvm.profile.model.ProfileUi
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun GestureScreen(
    viewModel: GestureViewModel,
    onBack: () -> Unit,
    onOpenProfile: (Long) -> Unit,
) {
    val profiles by viewModel.profiles.collectAsState()
    val configuration = LocalConfiguration.current
    val thresholdPx = configuration.screenWidthDp * 0.35f
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFE8B4C4),
                            Color(0xFF9C1D54),
                            Color(0xFF6B1038),
                        ),
                    ),
                ),
        ) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(end = 8.dp),
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
                    text = stringResource(R.string.daily_recommendations),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (profiles.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_profiles_left),
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onSurfaceVariant,
                )
            } else {
                val visible = profiles.take(3)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.TopCenter,
                ) {

                    for (depth in 2 downTo 0) {
                        val profile = visible.getOrNull(depth)
                        if (profile != null) {
                            key(profile.id, depth) {
                                StackedProfileCard(
                                    profile = profile,
                                    depth = depth,
                                    isTop = depth == 0,
                                    dismissThreshold = thresholdPx,
                                    onAccept = { viewModel.removeProfile(profile, accepted = true) },
                                    onReject = { viewModel.removeProfile(profile, accepted = false) },
                                    onOpenProfile = { onOpenProfile(profile.id) },
                                )
                            }
                        } else {
                            key("ghost_$depth") {
                                StackGhostCard(depth = depth)
                            }
                        }
                    }
                }
            }
        }
    }
}

private val StackCardShape = RoundedCornerShape(20.dp)

@Composable
private fun StackGhostCard(depth: Int) {
    val scheme = MaterialTheme.colorScheme
    val density = LocalDensity.current
    val horizontalInset = stackHorizontalInsetDp(depth)
    val scale = stackScale(depth)
    val stackOffsetY = with(density) { stackOffsetYDp(depth).roundToPx() }
    val shadowElevation = stackShadowDp(depth)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalInset)
            .padding(bottom = 8.dp)
            .zIndex((3 - depth).toFloat())
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0.5f, 0f)
                alpha = 1f - depth * 0.05f
            }
            .offset { IntOffset(0, stackOffsetY) }
            .shadow(shadowElevation, StackCardShape, spotColor = Color(0x40000000))
            .border(1.dp, scheme.outline.copy(alpha = 0.4f), StackCardShape),
        shape = StackCardShape,
        colors = CardDefaults.cardColors(containerColor = scheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = when (depth) {
                1 -> 5.dp
                else -> 3.dp
            },
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(scheme.surfaceVariant),
            )
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .height(12.dp)
                        .background(scheme.surfaceVariant, RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(scheme.surfaceVariant, RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .height(12.dp)
                        .background(scheme.surfaceVariant, RoundedCornerShape(4.dp)),
                )
            }
            HorizontalDivider(color = scheme.outline.copy(alpha = 0.35f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 72.dp, height = 20.dp)
                        .background(scheme.surfaceVariant, RoundedCornerShape(4.dp)),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(scheme.surfaceVariant, CircleShape),
                    )
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(scheme.primary.copy(alpha = 0.35f), CircleShape),
                    )
                }
            }
        }
    }
}

private fun stackHorizontalInsetDp(depth: Int) = (4 + depth * 10).dp

private fun stackOffsetYDp(depth: Int) = ((2 - depth) * 28).dp

private fun stackScale(depth: Int) = 1f - depth * 0.04f

private fun stackShadowDp(depth: Int) = when (depth) {
    0 -> 16.dp
    1 -> 9.dp
    else -> 5.dp
}

@Composable
private fun StackedProfileCard(
    profile: ProfileUi,
    depth: Int,
    isTop: Boolean,
    dismissThreshold: Float,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onOpenProfile: () -> Unit,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val scheme = MaterialTheme.colorScheme

    val horizontalInset = stackHorizontalInsetDp(depth)
    val scale = stackScale(depth)
    val stackOffsetY = with(density) { stackOffsetYDp(depth).roundToPx() }
    val shadowElevation = stackShadowDp(depth)

    val offsetX = remember(profile.id) { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(profile.id) {
        offsetX.snapTo(0f)
    }

    val dragModifier = if (isTop) {
        Modifier.pointerInput(profile.id) {
            detectDragGestures(
                onDragEnd = {
                    val x = offsetX.value
                    when {
                        x > dismissThreshold -> {
                            scope.launch {
                                offsetX.animateTo(1400f, tween(220))
                                onAccept()
                            }
                        }
                        x < -dismissThreshold -> {
                            scope.launch {
                                offsetX.animateTo(-1400f, tween(220))
                                onReject()
                            }
                        }
                        else -> {
                            scope.launch {
                                offsetX.animateTo(0f, tween(200))
                            }
                        }
                    }
                },
                onDrag = { change, dragAmount ->
                    change.consume()
                    scope.launch {
                        offsetX.snapTo(offsetX.value + dragAmount.x)
                    }
                },
            )
        }
    } else {
        Modifier
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalInset)
            .padding(bottom = 8.dp)
            .zIndex((3 - depth).toFloat())
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0.5f, 0f)
                alpha = 1f - depth * 0.04f
            }
            .offset { IntOffset(offsetX.value.roundToInt(), stackOffsetY) }
            .graphicsLayer {
                rotationZ = if (isTop) (offsetX.value / 35f).coerceIn(-14f, 14f) else 0f
            }
            .then(dragModifier)
            .shadow(shadowElevation, StackCardShape, spotColor = Color(0x40000000))
            .border(1.dp, scheme.outline.copy(alpha = 0.4f), StackCardShape),
        shape = StackCardShape,
        colors = CardDefaults.cardColors(containerColor = scheme.surface),
        elevation = CardDefaults.cardElevation(
            defaultElevation = when (depth) {
                0 -> 10.dp
                1 -> 5.dp
                else -> 2.dp
            },
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clickable(enabled = isTop) { onOpenProfile() },
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(profile.photoPreviewUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(scheme.surfaceVariant),
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color(0x99000000), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = stringResource(R.string.photo_count_format, profile.photoCount),
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable(enabled = isTop) { onOpenProfile() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (profile.isVerified) {
                        Text(
                            text = stringResource(R.string.verified_badge),
                            color = Color(0xFF1976D2),
                            fontSize = 12.sp,
                        )
                    }
                    if (profile.isPremiumNri) {
                        Text(
                            text = stringResource(R.string.premium_nri),
                            color = Color(0xFF7B1FA2),
                            fontSize = 12.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = profile.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = scheme.onSurface,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${profile.detailLine1} ${profile.detailLine2}",
                    fontSize = 14.sp,
                    color = scheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            HorizontalDivider(color = scheme.outline.copy(alpha = 0.35f), thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = scheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp),
                    )
                    Text(
                        text = stringResource(R.string.shortlist),
                        color = scheme.onSurfaceVariant,
                        fontSize = 14.sp,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = stringResource(R.string.like_her),
                        color = scheme.onSurfaceVariant,
                        fontSize = 14.sp,
                    )
                    if (isTop) {
                        FilledIconButton(
                            onClick = onReject,
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = scheme.surfaceVariant,
                                contentColor = scheme.onSurfaceVariant,
                            ),
                        ) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.no))
                        }
                        FilledIconButton(
                            onClick = onAccept,
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color(0xFFFF8A50),
                                contentColor = Color.White,
                            ),
                        ) {
                            Icon(Icons.Default.Check, contentDescription = stringResource(R.string.yes))
                        }
                    }
                }
            }
        }
    }
}
