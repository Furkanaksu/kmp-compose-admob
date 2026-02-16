// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "KMPAdMob",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "KMPAdMob",
            targets: ["KMPAdMob"]
        )
    ],
    dependencies: [
        .package(
            url: "https://github.com/googleads/swift-package-manager-google-mobile-ads",
            from: "11.0.0"
        )
    ],
    targets: [
        .target(
            name: "KMPAdMob",
            dependencies: [
                .product(
                    name: "GoogleMobileAds",
                    package: "swift-package-manager-google-mobile-ads"
                )
            ],
            path: "Sources/KMPAdMob"
        )
    ]
)
