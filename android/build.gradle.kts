buildscript {
    dependencies {
        classpath(BuildPlugins.Gradle.HiltGradlePlugin)
        classpath(BuildPlugins.Gradle.NavigationSafeArgsGradlePlugin)
    }
}

plugins {
    id(BuildPlugins.AndroidApplicationPlugin) version DependencyVersions.AndroidGradlePlugin.ANDROID_APPLICATION apply false
    id(BuildPlugins.AndroidLibraryGradlePlugin) version DependencyVersions.AndroidGradlePlugin.ANDROID_LIBRARY apply false
    id(BuildPlugins.KotlinAndroidPlugin) version DependencyVersions.Kotlin.KOTLIN apply false
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}