object BuildPlugins {

    object Gradle {
        const val HiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${DependencyVersions.GradlePlugin.HILT}"
        const val NavigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersions.GradlePlugin.NAVIGATION}"
    }

    const val AndroidLibraryGradlePlugin = "com.android.library"
    const val AndroidApplicationPlugin = "com.android.application"
    const val KotlinAndroidPlugin = "org.jetbrains.kotlin.android"
    const val HiltAndroidPlugin = "dagger.hilt.android.plugin"
    const val NavigationSafeArgsPlugin = "androidx.navigation.safeargs.kotlin"
    const val KotlinKaptPlugin = "kotlin-kapt"
}