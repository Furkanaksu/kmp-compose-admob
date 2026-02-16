package com.ilfuta.kmpads

/**
 * Cross-platform interstitial ad manager.
 *
 * Android: Create via [createInterstitialAdManager] passing an Android Context.
 * iOS: Create via [createInterstitialAdManager] passing any object (ignored on iOS).
 *
 * Usage:
 * ```kotlin
 * val adManager = createInterstitialAdManager(context)
 * adManager.loadAd(adUnitId = "ca-app-pub-xxx/yyy")
 * // later:
 * if (adManager.isAdReady()) adManager.showAd()
 * ```
 */
expect class InterstitialAdManager {
    fun loadAd(
        adUnitId: String,
        onAdLoaded: () -> Unit = {},
        onAdFailedToLoad: (String) -> Unit = {}
    )

    fun showAd(
        onAdDismissed: () -> Unit = {},
        onAdShowFailed: (String) -> Unit = {}
    )

    fun isAdReady(): Boolean
}

/**
 * Factory function — pass Android [Context] on Android, any value on iOS.
 */
expect fun createInterstitialAdManager(context: Any): InterstitialAdManager
