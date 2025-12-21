plugins {
	id("fabric-loom") version "1.14.7"
}

version = "${project.property("mod_version")}"
group = "${project.property("maven_group")}"

base {
	archivesName.set("${project.property("archives_base_name")}")
}

repositories {
	maven("https://maven.terraformersmc.com/releases/") {
		content {
			includeModule("com.terraformersmc", "modmenu")
		}
	}
	System.getenv("GRADLE_USE_MIRROR")?.let {
		maven(it)
	}
	mavenCentral()
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

	modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")

	include(implementation("tools.jackson.core:jackson-core:${project.property("jackson_version")}") as Dependency)
	include(implementation("com.fasterxml.jackson.core:jackson-annotations:${project.property("jackson_annotations_version")}") as Dependency)
	include(implementation("tools.jackson.core:jackson-databind:${project.property("jackson_version")}") as Dependency)

	compileOnly("org.jetbrains:annotations:${project.property("jetbrains_annotations_version")}")
}

tasks.processResources {
	val replaceProperties = mapOf(
		"version" to project.version
	)
	replaceProperties.forEach(inputs::property)

	filesMatching("fabric.mod.json") {
		expand(replaceProperties)
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.jar {
	from("../LICENSE")
}