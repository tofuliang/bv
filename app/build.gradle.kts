@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(gradleLibs.plugins.android.application)
    alias(gradleLibs.plugins.compose.compiler)
    alias(gradleLibs.plugins.google.ksp)
//    alias(gradleLibs.plugins.google.services) apply false
    alias(gradleLibs.plugins.kotlin.android)
    alias(gradleLibs.plugins.kotlin.serialization)
}

//if (AppConfiguration.googleServicesAvailable) {
//    apply(plugin = gradleLibs.plugins.google.services.get().pluginId)
//}


val signingProp = file(project.rootProject.file("signing.properties"))

android {
    signingConfigs {
        if (signingProp.exists()) {
            val properties = Properties().apply {
                load(FileInputStream(signingProp))
            }
            create("key") {
                storeFile = rootProject.file(properties.getProperty("keystore.path"))
                storePassword = properties.getProperty("keystore.pwd")
                keyAlias = properties.getProperty("keystore.alias")
                keyPassword = properties.getProperty("keystore.alias_pwd")
            }
        }
    }

    namespace = AppConfiguration.appId
    compileSdk = AppConfiguration.compileSdk

    defaultConfig {
        applicationId = AppConfiguration.appId
        minSdk = AppConfiguration.minSdk
        targetSdk = AppConfiguration.targetSdk
        versionCode = AppConfiguration.versionCode
        versionName = AppConfiguration.versionName
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add("channel")

    productFlavors {
        create("restricted") {
            dimension = "channel"
            applicationId = "dev.aaa1115910.bv.restricted"
        }
        create("lite") {
            dimension = "channel"
        }
        create("default") {
            dimension = "channel"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (signingProp.exists()) signingConfig = signingConfigs.getByName("key")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".debug"
        }
        create("r8Test") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".r8test"
            if (signingProp.exists()) signingConfig = signingConfigs.getByName("key")
        }
        create("alpha") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            if (signingProp.exists()) signingConfig = signingConfigs.getByName("key")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "**/*.proto"
        }

        if (gradle.startParameter.taskNames.find { it.startsWith("assembleLite") } != null) {
            jniLibs {
                val vlcLibs = listOf("libvlc", "libc++_shared", "libvlcjni")
                val abis = listOf("x86_64", "x86", "arm64-v8a", "armeabi-v7a")
                vlcLibs.forEach { vlcLibName -> abis.forEach { abi -> excludes.add("lib/$abi/$vlcLibName.so") } }
            }
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("x86_64", "x86", "arm64-v8a", "armeabi-v7a")
            isUniversalApk = true
        }
    }

    applicationVariants.configureEach {
        val variant = this
        outputs.configureEach {
            (this as ApkVariantOutputImpl).apply {
                val abi = this.filters.find { it.filterType == "ABI" }?.identifier ?: "universal"
                outputFileName =
                    "BV_${AppConfiguration.versionCode}_${AppConfiguration.versionName}.${variant.buildType.name}_${variant.flavorName}_$abi.apk"
                versionNameOverride =
                    "${variant.versionName}.${variant.buildType.name}"
            }
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_build_reports")
    stabilityConfigurationFiles.addAll(
        layout.projectDirectory.file("compose_compiler_config.conf")
    )
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(AppConfiguration.jdk))
    }
}

dependencies {
    // implementation(project(":app:mobile"))  // 移除mobile模块，构建纯TV版
    implementation(project(":app:tv"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
