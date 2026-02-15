package com.ilfuta.kmpads

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView

@Composable
actual fun CustomBannerAd(
    modifier: Modifier,
    adUnitId: String
) {
    UIKitView(
        factory = { getIOSBanner().view },
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .height(50.dp),
        update = { },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}
