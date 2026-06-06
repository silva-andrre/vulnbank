pluginManagement {
    repositories {
        maven { url = uri("https://dl.google.com/android/maven2/") }
        maven { url = uri("https://repo1.maven.org/maven2/") }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://dl.google.com/android/maven2/") }
        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
}

rootProject.name = "VulnBank"
include(":app")
