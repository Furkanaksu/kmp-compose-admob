package com.ilfuta.kmpads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Cross-platform AdMob banner ad composable.
 *
 * Usage:
 * ```kotlin
 * CustomBannerAd(
 *     modifier = Modifier.fillMaxWidth(),
 *     adUnitId = "ca-app-pub-xxx/yyy"
 * )
 * ```
 */
@Composable
expect fun CustomBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String
)
