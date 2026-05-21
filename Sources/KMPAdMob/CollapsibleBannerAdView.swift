import Foundation
import SwiftUI
import GoogleMobileAds

/// SwiftUI collapsible banner ad view wrapping GoogleMobileAds BannerView.
/// The banner is initially presented as a larger overlay and can be collapsed by the user.
public struct CollapsibleBannerAdView: UIViewRepresentable {

    public let adUnitId: String
    public let collapsiblePosition: String

    public init(adUnitId: String, collapsiblePosition: String = "bottom") {
        self.adUnitId = adUnitId
        self.collapsiblePosition = collapsiblePosition
    }

    public func makeUIView(context: Context) -> BannerView {
        let bannerView = BannerView()
        bannerView.adUnitID = adUnitId

        let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        if let rootViewController = windowScene?.windows.first?.rootViewController {
            bannerView.rootViewController = rootViewController
        }

        let extras = Extras()
        extras.additionalParameters = ["collapsible": collapsiblePosition]

        let request = Request()
        request.register(extras)

        bannerView.load(request)
        return bannerView
    }

    public func updateUIView(_ uiView: BannerView, context: Context) {}
}
