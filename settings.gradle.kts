rootProject.name = "kmp-compose-admob"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// NOTE: No dependencyResolutionManagement here.
// When used as an includeBuild (composite build), the parent project's
// repositories are used. When built standalone, repositories are declared
// in the root build.gradle.kts via allprojects { repositories { ... } }.
// Single-module project: JitPack publishes as com.github.Furkanaksu:kmp-compose-admob:VERSION
