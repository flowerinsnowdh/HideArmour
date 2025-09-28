plugins {
	id("fabric-loom").version("1.12.0-alpha.20")
	id("java")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
	archivesName.set(project.property("archives_base_name") as String)
}

repositories {
	if (System.getenv("GRADLE_USE_MIRROR") == "true") {
		maven("https://repo.nju.edu.cn/maven/")
	}
	mavenCentral()
	maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
	modImplementation("com.terraformersmc:modmenu:15.0.0")

	include(implementation("com.electronwill.night-config:core:${project.property("night_config_version")}") as Dependency)
	include(implementation("com.electronwill.night-config:toml:${project.property("night_config_version")}") as Dependency)
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

tasks.withType<JavaCompile>().configureEach {
	options.encoding = "UTF-8"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

tasks.jar {
	from("../LICENSE")
}