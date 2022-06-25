object DependencyVersions {

    object GradlePlugin {
        const val HILT = "2.40"
        const val NAVIGATION = "2.4.1"
    }

    object AndroidGradlePlugin {
        private const val ANDROID_GRADLE_PLUGIN = "7.1.2"
        const val ANDROID_APPLICATION = ANDROID_GRADLE_PLUGIN
        const val ANDROID_LIBRARY = ANDROID_GRADLE_PLUGIN
    }

    object Kotlin {
        const val KOTLIN = "1.6.21"
        const val COROUTINES = "1.6.1"
    }

    object AndroidX {
        const val CORE = "1.7.0"
        const val APPCOMPAT = "1.4.1"
        const val CONSTRAINT_LAYOUT = "2.1.4"
        const val VIEWPAGER2 = "1.0.0"
        const val DATASTORE = "1.0.0"
        const val ROOM = "2.4.2"
        const val LIFECYCLE = "2.4.1"
        const val NAVIGATION = GradlePlugin.NAVIGATION
    }

    object Google {
        const val MATERIAL = "1.4.0"
        const val HILT = GradlePlugin.HILT
    }

    object Tools {
        const val DESUGARING = "1.1.5"
    }

    object Test {
        const val JUNIT4 = "4.13.2"
        const val HAMCREST = "2.2"
        const val EXT_JUNIT = "1.1.3"
        const val ESPRESSO = "3.4.0"
    }
}