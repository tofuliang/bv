@file:Suppress("UnstableApiUsage")

plugins {
    alias(gradleLibs.plugins.android.library)
    alias(gradleLibs.plugins.compose.compiler)
    alias(gradleLibs.plugins.kotlin.android)
}

android {
    namespace = "${AppConfiguration.appId}.player"
    compileSdk = AppConfiguration.compileSdk

    defaultConfig {
        minSdk = AppConfiguration.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "libVLCVersion", "\"${AppConfiguration.libVLCVersion}\"")
    }

    flavorDimensions.add(FlavorConfiguration.FLAVOR_DIMENSION)

    productFlavors {
        create(FlavorConfiguration.FLAVOR_RESTRICTED) {
            dimension = FlavorConfiguration.FLAVOR_DIMENSION
        }
        create(FlavorConfiguration.FLAVOR_LITE) {
            dimension = FlavorConfiguration.FLAVOR_DIMENSION
        }
        create(FlavorConfiguration.FLAVOR_DEFAULT) {
            dimension = FlavorConfiguration.FLAVOR_DIMENSION
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("r8Test") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("alpha") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    api(project(":player:core"))
    api(project(":player:shared"))
    // api(project(":player:mobile"))  // 移除mobile依赖，构建纯TV版
    api(project(":player:tv"))
}
