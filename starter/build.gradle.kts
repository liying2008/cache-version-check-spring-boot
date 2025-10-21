/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot.optional-dependencies")
    id("com.vanniktech.maven.publish")
}

group = extra.get("GROUP") as String
version = extra.get("VERSION_NAME") as String

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {
    api(project(":agreement"))
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    optional("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
    archiveClassifier.set("")
    manifest {
        attributes(mapOf("Implementation-Title" to "Spring Boot Starter for Cache Version Check"))
        attributes(mapOf("Implementation-Version" to archiveVersion))
        attributes(mapOf("Implementation-Vendor" to "Li Ying"))
        attributes(mapOf("Built-By" to System.getProperty("user.name")))
        attributes(mapOf("Built-JDK" to System.getProperty("java.version")))
        attributes(mapOf("Built-Gradle" to gradle.gradleVersion))
    }
}
