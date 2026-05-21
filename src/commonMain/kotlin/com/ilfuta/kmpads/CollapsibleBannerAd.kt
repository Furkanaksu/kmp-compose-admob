package com.ilfuta.kmpads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Collapsible banner position.
 * Determines whether the banner appears at the top or bottom of the screen.
 */
enum class CollapsibleBannerPosition {
    TOP,
    BOTTOM
}

/**
 * Cross-platform AdMob collapsible banner ad composable.
 *
 * Collapsible banners are initially presented as a larger overlay and can be
 * collapsed to a smaller banner by the user.
 *
 * Usage:
 * ```kotlin
 * CollapsibleBannerAd(
 *     modifier = Modifier.fillMaxWidth(),
 *     adUnitId = "ca-app-pub-xxx/yyy",
 *     collapsiblePosition = CollapsibleBannerPosition.BOTTOM
 * )
 * ```
 */
@Composable
expect fun CollapsibleBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String,
    collapsiblePosition: CollapsibleBannerPosition = CollapsibleBannerPosition.BOTTOM
)
