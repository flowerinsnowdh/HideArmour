pluginManagement {
    repositories {
        System.getenv("GRADLE_CENTRAL_MIRROR")?.let {
            maven(it)
        }
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
