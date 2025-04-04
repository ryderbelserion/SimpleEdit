plugins {
    alias(libs.plugins.runPaper)

    id("paper-plugin")
}

project.group = "${rootProject.group}.paper"
project.version = rootProject.version
project.description = "A version of GradleTemplate for Paper based servers!"

tasks {
    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to rootProject.properties["website"].toString())

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}