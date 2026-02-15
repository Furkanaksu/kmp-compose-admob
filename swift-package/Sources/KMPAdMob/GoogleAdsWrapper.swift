import UIKit
import GoogleMobileAds

/// Singleton managing interstitial ad lifecycle for the KMP bridge.
@objc public class GoogleAdsWrapper: NSObject {

    @objc public static let shared = GoogleAdsWrapper()

    private var interstitialAd: InterstitialAd?
    private var onAdDismissedCallback: (() -> Void)?
    private var onAdShowFailedCallback: ((String) -> Void)?

    private override init() {
        super.init()
    }

    // MARK: - Interstitial Ad

    @objc public func loadInterstitialAd(
        adUnitId: String,
        onAdLoaded: @escaping () -> Void,
        onAdFailedToLoad: @escaping (String) -> Void
    ) {
        InterstitialAd.load(with: adUnitId, request: Request()) { [weak self] ad, error in
            guard let self = self else { return }
            if let error = error {
                self.interstitialAd = nil
                onAdFailedToLoad(error.localizedDescription)
                return
            }
            self.interstitialAd = ad
            self.interstitialAd?.fullScreenContentDelegate = self
            onAdLoaded()
        }
    }

    @objc public func showInterstitialAd(
        onAdDismissed: @escaping () -> Void,
        onAdShowFailed: @escaping (String) -> Void
    ) {
        self.onAdDismissedCallback = onAdDismissed
        self.onAdShowFailedCallback = onAdShowFailed

        guard let ad = interstitialAd else {
            onAdShowFailed("Ad not loaded")
            return
        }

        guard
            let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
            let rootViewController = windowScene.windows.first?.rootViewController
        else {
            onAdShowFailed("Root view controller not found")
            return
        }

        ad.present(from: rootViewController)
    }

    @objc public func isInterstitialAdReady() -> Bool {
        return interstitialAd != nil
    }
}

// MARK: - FullScreenContentDelegate

extension GoogleAdsWrapper: FullScreenContentDelegate {
    public func adDidDismissFullScreenContent(_ ad: FullScreenPresentingAd) {
        interstitialAd = nil
        onAdDismissedCallback?()
    }

    public func ad(_ ad: FullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
        interstitialAd = nil
        onAdShowFailedCallback?(error.localizedDescription)
    }
}
