plugins {
	id("fabric-loom").version("1.11-SNAPSHOT")
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
	maven("https://maven.pkg.github.com/CarmJos/configured") {
		credentials {
			username = "x-access-token"
			password = System.getenv("GITHUB_PKG_R_TOKEN")
		}
	}
	maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
	modImplementation("com.terraformersmc:modmenu:15.0.0-beta.3")

	include(implementation("cc.carm.lib:configured-core:${project.property("configured_version")}") as Dependency)
	include(implementation("cc.carm.lib:configured-feature-file:${project.property("configured_version")}") as Dependency)
	include(implementation("cc.carm.lib:configured-feature-section:${project.property("configured_version")}") as Dependency)
	include(implementation("cc.carm.lib:configured-feature-commentable:${project.property("configured_version")}") as Dependency)
	include(implementation("cc.carm.lib:yamlcommentwriter:1.2.1") as Dependency)
	include(implementation("org.yaml:snakeyaml:2.4") as Dependency)
	include(implementation("cc.carm.lib:configured-yaml:${project.property("configured_version")}") as Dependency)
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
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
	from("../LICENSE")
}