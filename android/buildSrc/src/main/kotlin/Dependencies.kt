import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    // Tools
    private const val desugaring = "com.android.tools:desugar_jdk_libs:${DependencyVersions.Tools.DESUGARING}"

    // AndroidX
    private const val coreKtx = "androidx.core:core-ktx:${DependencyVersions.AndroidX.CORE}"
    private const val appCompat = "androidx.appcompat:appcompat:${DependencyVersions.AndroidX.APPCOMPAT}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersions.AndroidX.CONSTRAINT_LAYOUT}"
    private const val viewPager2 = "androidx.viewpager2:viewpager2:${DependencyVersions.AndroidX.VIEWPAGER2}"
    private const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersions.AndroidX.LIFECYCLE}"
    private const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersions.AndroidX.LIFECYCLE}"
    private const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersions.AndroidX.LIFECYCLE}"
    private const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${DependencyVersions.AndroidX.NAVIGATION}"
    private const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${DependencyVersions.AndroidX.NAVIGATION}"
    private const val roomKtx = "androidx.room:room-ktx:${DependencyVersions.AndroidX.ROOM}"
    private const val roomRuntime = "androidx.room:room-runtime:${DependencyVersions.AndroidX.ROOM}"
    private const val roomKapt = "androidx.room:room-compiler:${DependencyVersions.AndroidX.ROOM}"
    private const val roomTest = "androidx.room:room-testing:${DependencyVersions.AndroidX.ROOM}"
    private const val preferencesDataStore = "androidx.datastore:datastore-preferences:${DependencyVersions.AndroidX.DATASTORE}"

    // Google
    private const val material = "com.google.android.material:material:${DependencyVersions.Google.MATERIAL}"
    private const val hilt = "com.google.dagger:hilt-android:${DependencyVersions.Google.HILT}"
    private const val hiltTest = "com.google.dagger:hilt-android-testing:${DependencyVersions.Google.HILT}"
    private const val hiltAndroidTest = hiltTest
    private const val hiltKapt = "com.google.dagger:hilt-compiler:${DependencyVersions.Google.HILT}"
    private const val hiltKaptTest = hiltKapt
    private const val hiltKaptAndroidTest = hiltKapt

    // Kotlin
    private const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.Kotlin.COROUTINES}"
    private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersions.Kotlin.COROUTINES}"
    private const val coroutinesAndroidTest = coroutinesTest

    // Test
    private const val junit = "junit:junit:${DependencyVersions.Test.JUNIT4}"
    private const val hamcrest ="org.hamcrest:hamcrest:${DependencyVersions.Test.HAMCREST}"
    private const val extJunit = "androidx.test.ext:junit:${DependencyVersions.Test.EXT_JUNIT}"
    private const val espresso = "androidx.test.espresso:espresso-core:${DependencyVersions.Test.ESPRESSO}"

    val coreLibraryDesugaring = listOf(
        desugaring
    )

    val appImplementations = listOf(
        coreKtx, appCompat, constraintLayout, viewPager2, lifecycleRuntimeKtx, lifecycleLiveDataKtx, lifecycleViewModelKtx,
        navigationFragmentKtx, navigationUiKtx, roomRuntime, roomKtx, preferencesDataStore,
        material, hilt, coroutines
    )

    val appKapts = listOf(
        roomKapt, hiltKapt
    )

    val testImplementations = listOf(
        coroutinesTest, roomTest, hiltTest, junit, hamcrest
    )

    val testKapts = listOf(
        hiltKaptTest
    )

    val androidTestImplementations = listOf(
        coroutinesAndroidTest, hiltAndroidTest, extJunit, espresso
    )

    val androidTestKapts = listOf(
        hiltKaptAndroidTest
    )
}
fun DependencyHandler.coreLibraryDesugraing(libraries: List<String>) {
    libraries.forEach { add("coreLibraryDesugaring", it) }
}

fun DependencyHandler.kapt(libraries: List<String>) {
    libraries.forEach { add("kapt", it) }
}

fun DependencyHandler.kaptTest(libraries: List<String>) {
    libraries.forEach { add("kaptTest", it) }
}

fun DependencyHandler.kaptAndroidTest(libraries: List<String>) {
    libraries.forEach { add("kaptAndroidTest", it) }
}

fun DependencyHandler.implementation(libraries: List<String>) {
    libraries.forEach { add("implementation", it) }
}

fun DependencyHandler.testImplementation(libraries: List<String>) {
    libraries.forEach { add("testImplementation", it) }
}

fun DependencyHandler.androidTestImplementation(libraries: List<String>) {
    libraries.forEach { add("androidTestImplementation", it) }
}
