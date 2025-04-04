plugins {
    id("root-plugin")
}

rootProject.group = "com.ryderbelserion.gradle"

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "0.1.0"