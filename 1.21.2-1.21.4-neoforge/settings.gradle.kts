pluginManagement {
    repositories {
        if (System.getenv("GRADLE_USE_MIRROR") == "true") {
            maven("https://repo.nju.edu.cn/maven/")
        }
        gradlePluginPortal()
        maven("https://maven.neoforged.net/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
