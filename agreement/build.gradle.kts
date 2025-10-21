/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish")
}

group = extra.get("GROUP") as String
version = extra.get("VERSION_NAME") as String

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    manifest {
        attributes(mapOf("Implementation-Title" to "Cache Version Check Agreement"))
        attributes(mapOf("Implementation-Version" to archiveVersion))
        attributes(mapOf("Implementation-Vendor" to "Li Ying"))
        attributes(mapOf("Built-By" to System.getProperty("user.name")))
        attributes(mapOf("Built-JDK" to System.getProperty("java.version")))
        attributes(mapOf("Built-Gradle" to gradle.gradleVersion))
    }
}
