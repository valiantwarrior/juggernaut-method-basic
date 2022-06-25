plugins {
    id(BuildPlugins.AndroidApplicationPlugin)
    id(BuildPlugins.KotlinAndroidPlugin)
    id(BuildPlugins.HiltAndroidPlugin)
    id(BuildPlugins.NavigationSafeArgsPlugin)
    id(BuildPlugins.KotlinKaptPlugin)
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "kr.valor.juggernaut"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "kr.valor.juggernaut.HiltTestRunner"
    }
    viewBinding {
        android.buildFeatures.viewBinding = true
    }
    dataBinding {
        android.buildFeatures.dataBinding = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugraing(Dependencies.coreLibraryDesugaring)

    kapt(Dependencies.appKapts)
    kaptTest(Dependencies.testKapts)
    kaptAndroidTest(Dependencies.androidTestKapts)

    implementation(Dependencies.appImplementations)
    testImplementation(Dependencies.testImplementations)
    androidTestImplementation(Dependencies.androidTestImplementations)
}