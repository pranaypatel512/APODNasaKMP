import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(11)
    }
    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }
    iosTarget("iOS") {}

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation(libs.kotlinX.coroutines)

            api(libs.koin.core)

            api(libs.ktor.core)
            api(libs.ktor.cio)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)

            implementation(libs.kotlinX.serializationJson)

            implementation(libs.multiplatformSettings.noArg)
            implementation(libs.multiplatformSettings.coroutines)

            api(libs.napier)

            implementation(libs.kotlinX.dateTime)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
        }
        sourceSets["commonTest"].dependencies {
            implementation(kotlin("test"))
        }

        sourceSets["androidMain"].dependencies {
            api(libs.appCompat)
            api(libs.androidX.core)
        }

        sourceSets["androidTest"].dependencies {
            implementation(libs.jUnit)
        }
        sourceSets["desktopMain"].dependencies {
            api(compose.preview)
        }
        sourceSets["desktopTest"].dependencies {
        }
    }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}