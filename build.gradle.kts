import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
    signing
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.bspoones.zeus"
version = "1.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.2.1-jre")
    implementation("org.spongepowered:configurate-gson:4.1.2")
    implementation("org.slf4j:slf4j-api:1.7.32") // SLF4J dependency
    implementation("net.dv8tion:JDA:5.0.0-beta.17") // JDA
    implementation ("io.github.cdimascio:java-dotenv:5.2.2") // Dotenv
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}


tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "org.bspoones.zeus.Zeus"
    }
    mergeServiceFiles()
    from(sourceSets["main"].output)
    configurations.forEach { configuration ->
        from(configuration)
    }
}

tasks.named("build") {
    dependsOn("shadowJar")
    dependsOn("sourcesJar")
}
