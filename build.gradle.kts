plugins {
    kotlin("jvm") version "1.9.0"
    id("maven-publish")
}

group = "org.bspoones.zeus"
version = "1.2"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.slf4j:slf4j-api:1.7.32") // SLF4J dependency
    implementation("ch.qos.logback:logback-classic:1.2.9") // Logback logging implementation
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

tasks.named("build") {
    dependsOn("sourcesJar")
}