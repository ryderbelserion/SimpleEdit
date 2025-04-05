plugins {
    id("root-plugin")
}

rootProject.group = "com.ryderbelserion.simpleedit"

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "1.0.0"