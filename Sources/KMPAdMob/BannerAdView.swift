import Foundation
import SwiftUI
import GoogleMobileAds

/// SwiftUI banner ad view wrapping GoogleMobileAds BannerView.
/// Pass your production or test ad unit ID.
public struct BannerAdView: UIViewRepresentable {

    public let adUnitId: String

    public init(adUnitId: String) {
        self.adUnitId = adUnitId
    }

    public func makeUIView(context: Context) -> BannerView {
        let bannerView = BannerView()
        bannerView.adUnitID = adUnitId

        let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        if let rootViewController = windowScene?.windows.first?.rootViewController {
            bannerView.rootViewController = rootViewController
        }

        bannerView.load(Request())
        return bannerView
    }

    public func updateUIView(_ uiView: BannerView, context: Context) {}
}
