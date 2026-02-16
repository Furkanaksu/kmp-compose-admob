package com.ilfuta.kmpads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

actual fun createInterstitialAdManager(context: Any): InterstitialAdManager {
    return InterstitialAdManager(context as Context)
}

actual class InterstitialAdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false

    actual fun loadAd(
        adUnitId: String,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (String) -> Unit
    ) {
        if (isLoading || interstitialAd != null) {
            Log.d("KMPAds", "Interstitial ad is already loading or loaded")
            return
        }

        isLoading = true
        InterstitialAd.load(
            context,
            adUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    onAdLoaded()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                    onAdFailedToLoad("${error.message} (code: ${error.code})")
                }
            }
        )
    }

    actual fun showAd(
        onAdDismissed: () -> Unit,
        onAdShowFailed: (String) -> Unit
    ) {
        val ad = interstitialAd ?: run {
            onAdShowFailed("No ad loaded")
            return
        }

        val activity = context.findActivity() ?: run {
            onAdShowFailed("Could not find Activity from context")
            return
        }

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                onAdShowFailed(error.message)
            }
        }

        ad.show(activity)
    }

    actual fun isAdReady(): Boolean = interstitialAd != null

    private fun Context.findActivity(): Activity? {
        var ctx = this
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return ctx
            ctx = ctx.baseContext
        }
        return null
    }
}
