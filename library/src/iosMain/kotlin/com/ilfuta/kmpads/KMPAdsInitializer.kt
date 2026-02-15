package com.ilfuta.kmpads

import platform.UIKit.UIViewController

/**
 * iOS bridge variables. These must be set by Swift code before using any ad composables.
 *
 * The KMPAdMob Swift Package provides [KMPAdMobSetup] helper to wire these up in one call.
 *
 * Example (Swift):
 * ```swift
 * import composeApp
 * import KMPAdMob
 *
 * KMPAdsInitializerKt.IOSBanner = KMPAdMobSetup.defaultBannerFactory(adUnitId: "ca-app-pub-xxx/yyy")
 * KMPAdsInitializerKt.IOSLoadInterstitialAd = GoogleAdsWrapper.shared.loadInterstitialAd
 * KMPAdsInitializerKt.IOSShowInterstitialAd = GoogleAdsWrapper.shared.showInterstitialAd
 * KMPAdsInitializerKt.IOSIsInterstitialAdReady = GoogleAdsWrapper.shared.isInterstitialAdReady
 * ```
 */
lateinit var IOSBanner: () -> UIViewController
lateinit var IOSLoadInterstitialAd: (String, () -> Unit, (String) -> Unit) -> Unit
lateinit var IOSShowInterstitialAd: (() -> Unit, (String) -> Unit) -> Unit
lateinit var IOSIsInterstitialAdReady: () -> Boolean

internal fun getIOSBanner(): UIViewController = IOSBanner()

internal fun loadIOSInterstitialAd(
    adUnitId: String,
    onAdLoaded: () -> Unit,
    onAdFailedToLoad: (String) -> Unit
) = IOSLoadInterstitialAd(adUnitId, onAdLoaded, onAdFailedToLoad)

internal fun showIOSInterstitialAd(
    onAdDismissed: () -> Unit,
    onAdShowFailed: (String) -> Unit
) = IOSShowInterstitialAd(onAdDismissed, onAdShowFailed)

internal fun isIOSInterstitialAdReady(): Boolean = IOSIsInterstitialAdReady()
