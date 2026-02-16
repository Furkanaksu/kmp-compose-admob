package com.ilfuta.kmpads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
actual fun CustomBannerAd(
    modifier: Modifier,
    adUnitId: String
) {
    val context = LocalContext.current
    var adError by remember { mutableStateOf<String?>(null) }

    val configuration = LocalConfiguration.current
    val adWidthPx = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }

    if (adError == null) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            factory = { ctx ->
                val adView = AdView(ctx)

                val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    ctx,
                    (adWidthPx / ctx.resources.displayMetrics.density).toInt()
                )

                adView.setAdSize(adSize)
                adView.adUnitId = adUnitId

                adView.adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        adError = null
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        adError = loadAdError.message
                    }
                }

                adView.loadAd(AdRequest.Builder().build())
                adView
            }
        )
    }
}
