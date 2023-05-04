import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    alias(libs.plugins.buildconfig)
    kotlin("native.cocoapods")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.8.20"
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "common"
            isStatic = true
            export("com.arkivanov.decompose:decompose:2.0.0-compose-experimental-alpha-02")
            export("com.arkivanov.decompose:extensions-compose-jetbrains:2.0.0-compose-experimental-alpha-02")
            export("com.arkivanov.essenty:parcelable:1.1.0")
        }
        extraSpecAttributes["resources"] =
            "['src/commonMain/resources/**', 'src/iOSMain/resources/**']"
    }

    jvm("desktop") {
        jvmToolchain(11)
    }

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

            implementation(libs.napier)

            implementation(libs.kotlinX.dateTime)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(libs.decompose)
            api(libs.decompose.compose.jetbrains)
            api(libs.essenty)
        }

        sourceSets["androidMain"].dependencies {
            api(libs.appCompat)
            api(libs.androidX.core)
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        sourceSets.create("iOSMain").apply {
            dependsOn(sourceSets.getByName("commonMain"))
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                api(libs.ktor.darwin)
            }
        }
    }
}

buildConfig{
    buildConfigField(
        "String",
        "API_KEY",
        gradleLocalProperties(rootDir).getProperty("api_key") ?: ""
    )
}
android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.example.common"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        jvmToolchain(11)
    }
}