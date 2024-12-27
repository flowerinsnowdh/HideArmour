plugins {
    id("java")
    id("maven-publish")
}

group = "cn.flowerinsnow.hidearmour"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.electronwill.night-config:toml:${property("version_night_config")}")
    compileOnly("org.jetbrains:annotations:${property("version_annotations")}")

    testImplementation(platform("org.junit:junit-bom:${property("version_junit")}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }

    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/flowerinsnow-lights-opensource/HideArmour")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
