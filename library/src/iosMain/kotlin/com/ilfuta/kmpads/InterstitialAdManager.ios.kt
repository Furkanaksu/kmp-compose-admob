package com.ilfuta.kmpads

actual fun createInterstitialAdManager(context: Any): InterstitialAdManager {
    return InterstitialAdManager()
}

actual class InterstitialAdManager {
    private var isLoading = false

    actual fun loadAd(
        adUnitId: String,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (String) -> Unit
    ) {
        if (isLoading) return
        isLoading = true

        loadIOSInterstitialAd(
            adUnitId = adUnitId,
            onAdLoaded = {
                isLoading = false
                onAdLoaded()
            },
            onAdFailedToLoad = { error ->
                isLoading = false
                onAdFailedToLoad(error)
            }
        )
    }

    actual fun showAd(
        onAdDismissed: () -> Unit,
        onAdShowFailed: (String) -> Unit
    ) {
        showIOSInterstitialAd(
            onAdDismissed = onAdDismissed,
            onAdShowFailed = onAdShowFailed
        )
    }

    actual fun isAdReady(): Boolean = isIOSInterstitialAdReady()
}
