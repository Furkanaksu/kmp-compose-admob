import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    `maven-publish`
}

group = "com.ilfuta.kmpads"
version = "1.0.3"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.forEach { binary ->
            binary.linkerOpts.addAll(
                listOf(
                    "-framework", "GoogleMobileAds",
                    "-framework", "AdSupport",
                )
            )
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }

        androidMain.dependencies {
            implementation(libs.play.services.ads)
        }
    }
}

android {
    namespace = "com.ilfuta.kmpads"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Repositories for standalone builds (not needed when used as includeBuild)
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubMaven"
            // GitHub Actions publish target: build/repo → maven branch
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
    publications {
        withType<MavenPublication> {
            pom {
                name.set("kmp-compose-admob")
                description.set("Kotlin Multiplatform AdMob integration for Compose (Banner + Interstitial)")
                url.set("https://github.com/Furkanaksu/kmp-compose-admob")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("Furkanaksu")
                        name.set("Furkan Aksu")
                    }
                }
            }
        }
    }
}
