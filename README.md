# kmp-compose-admob

Kotlin Multiplatform (KMP) + Compose Multiplatform AdMob entegrasyonu.
**Banner** ve **Interstitial** reklamları hem Android hem iOS için tek API ile kullanın.

[![](https://jitpack.io/v/Furkanaksu/kmp-compose-admob.svg)](https://jitpack.io/#Furkanaksu/kmp-compose-admob)

---

## Özellikler

- `CustomBannerAd` — Adaptive banner composable (Android + iOS)
- `InterstitialAdManager` — Geçiş reklamı yöneticisi (Android + iOS)
- KMP `expect/actual` mimarisi
- Sıfır boilerplate kullanım

---

## Kurulum

### 1. Android — `build.gradle.kts`

```kotlin
repositories {
    maven("https://jitpack.io")
}

// commonMain veya androidMain dependencies bloğuna:
implementation("com.github.Furkanaksu:kmp-compose-admob:1.0.0")
```

### 2. Android — `AndroidManifest.xml`

```xml
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX" />
```

### 3. Android — `Application.onCreate()`

```kotlin
import com.google.android.gms.ads.MobileAds

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
```

---

### 4. iOS — Swift Package

Xcode'da **File → Add Package Dependencies** menüsünden:

```
https://github.com/Furkanaksu/kmp-compose-admob
```

> Package klasörü: `swift-package/`

SPM package `GoogleMobileAds`'i otomatik çeker.

### 5. iOS — `Info.plist`

```xml
<key>GADApplicationIdentifier</key>
<string>ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX</string>
```

### 6. iOS — `AppDelegate`

```swift
import GoogleMobileAds

func application(_ application: UIApplication,
                 didFinishLaunchingWithOptions ...) -> Bool {
    MobileAds.shared.start(completionHandler: nil)
    return true
}
```

### 7. iOS — `ContentView.swift` (Bridge kurulumu)

```swift
import composeApp   // KMP framework'ünüz
import KMPAdMob

struct ComposeView: UIViewControllerRepresentable {
    init() {
        KMPAdsInitializerKt.IOSBanner = KMPAdMobSetup.defaultBannerFactory(
            adUnitId: "ca-app-pub-xxx/banner-id"
        )
        KMPAdsInitializerKt.IOSLoadInterstitialAd  = GoogleAdsWrapper.shared.loadInterstitialAd
        KMPAdsInitializerKt.IOSShowInterstitialAd  = GoogleAdsWrapper.shared.showInterstitialAd
        KMPAdsInitializerKt.IOSIsInterstitialAdReady = {
            KotlinBoolean(bool: GoogleAdsWrapper.shared.isInterstitialAdReady())
        }
    }

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
```

---

## Kullanım

### Banner Reklam

```kotlin
import com.ilfuta.kmpads.CustomBannerAd

// Herhangi bir Composable içinde:
CustomBannerAd(
    modifier = Modifier.fillMaxWidth(),
    adUnitId = "ca-app-pub-xxx/banner-id"
)
```

### Interstitial Reklam

```kotlin
import com.ilfuta.kmpads.createInterstitialAdManager

// ViewModel veya Composable içinde:
val adManager = remember { createInterstitialAdManager(context) }

LaunchedEffect(Unit) {
    adManager.loadAd(
        adUnitId = "ca-app-pub-xxx/interstitial-id",
        onAdLoaded = { /* hazır */ },
        onAdFailedToLoad = { error -> /* hata */ }
    )
}

// Göster:
if (adManager.isAdReady()) {
    adManager.showAd(
        onAdDismissed = { /* kapandı */ },
        onAdShowFailed = { error -> /* hata */ }
    )
}
```

---

## Test Ad Unit ID'leri

| Tür         | Test ID                                      |
|-------------|----------------------------------------------|
| Banner      | `ca-app-pub-3940256099942544/2435281174`     |
| Interstitial| `ca-app-pub-3940256099942544/1033173712`     |

---

## Sürümler

| Versiyon | Notlar              |
|----------|---------------------|
| 1.0.0    | İlk yayın           |

---

## Lisans

MIT License
