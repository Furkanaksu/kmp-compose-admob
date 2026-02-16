import UIKit
import SwiftUI
import GoogleMobileAds

/// Helper for wiring up KMP AdMob bridge variables in one call.
///
/// Call this inside your `ComposeView` / `UIViewControllerRepresentable` init,
/// assigning each closure to the corresponding `KMPAdsInitializerKt` property.
///
/// Example:
/// ```swift
/// import composeApp   // your KMP framework
/// import KMPAdMob
///
/// struct ComposeView: UIViewControllerRepresentable {
///     init() {
///         KMPAdsInitializerKt.IOSBanner = KMPAdMobSetup.defaultBannerFactory(
///             adUnitId: "ca-app-pub-xxx/yyy"
///         )
///         KMPAdsInitializerKt.IOSLoadInterstitialAd  = GoogleAdsWrapper.shared.loadInterstitialAd
///         KMPAdsInitializerKt.IOSShowInterstitialAd  = GoogleAdsWrapper.shared.showInterstitialAd
///         KMPAdsInitializerKt.IOSIsInterstitialAdReady = {
///             KotlinBoolean(bool: GoogleAdsWrapper.shared.isInterstitialAdReady())
///         }
///     }
///     ...
/// }
/// ```
public class KMPAdMobSetup {

    private init() {}

    /// Returns a closure that creates a `UIViewController` containing a banner ad.
    /// Pass this to `KMPAdsInitializerKt.IOSBanner`.
    public static func defaultBannerFactory(adUnitId: String) -> () -> UIViewController {
        return {
            let view = BannerAdView(adUnitId: adUnitId)
            return UIHostingController(rootView: AnyView(view))
        }
    }
}
