import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

plugins {
    kotlin("jvm") version "1.9.22"
}

group = "dev.sakkke"
//version = "0.1.0-SNAPSHOT"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit
        classpath("org.eclipse.jgit:org.eclipse.jgit:6.9.0.202403050737-r")
    }
}

val setVersionFromGit = tasks.register("setVersionFromGit") {
    doLast {
        val repo = FileRepositoryBuilder.create(File(".git"))
        val git = Git(repo)
        val describe = git.describe().setTags(true).call()
        if (describe != null) {
            project.version = describe
        } else {
            project.version = "0.1.0-SNAPSHOT"
        }
        println("Project version set to: ${project.version}")
    }
}

tasks.named("compileKotlin") {
    dependsOn(setVersionFromGit)
}

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    // https://mvnrepository.com/artifact/org.quartz-scheduler/quartz
    implementation("org.quartz-scheduler:quartz:2.3.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    from({
        configurations.runtimeClasspath.get().filter { it.isDirectory }.map { it }
        configurations.runtimeClasspath.get().filter { !it.isDirectory }.map { zipTree(it) }
    })
}
kotlin {
    jvmToolchain(17)
}