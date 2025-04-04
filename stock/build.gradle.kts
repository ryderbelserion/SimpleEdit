plugins {
    id("root-plugin")

    application
}

application {
    mainClass.set("${rootProject.group}.${rootProject.name}")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = application.mainClass
        }
    }
}