/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

plugins {
    id("org.springframework.boot") version "2.7.6" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.21" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.6.21" apply false
    id("org.jreleaser") version "1.20.0" apply false
}

subprojects {
    project.buildDir = file("${rootProject.buildDir.absolutePath}/${project.name}")
}

allprojects {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }

    tasks.withType<JavaCompile>() {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
    }
}


tasks.register<Delete>("cleanAll") {
    delete(rootProject.buildDir)
}
