dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "KartSearch"
include (":presentation")
include (":data")
include (":domain")
