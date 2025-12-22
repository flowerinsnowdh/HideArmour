pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		if (System.getenv("GRADLE_USE_MIRROR") == "true") {
			maven("https://repo.nju.edu.cn/maven/")
		} else {
			gradlePluginPortal()
			mavenCentral()
		}
	}
}