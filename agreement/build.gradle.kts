/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jreleaser.model.Active

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jreleaser")
    `maven-publish`
}

group = "cc.duduhuo"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "cache-version-check-agreement"

            from(components["java"])

            pom {
                name = "Cache Version Check - Agreement"
                description = "A agreement for automatically checks and manages schema version compatibility between entity classes and cache storage to prevent business exceptions caused by outdated cached data."
                url = "https://github.com/liying2008/cache-version-check-spring-boot"
                inceptionYear = "2025"
                packaging = "jar"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/license/MIT"
                    }
                }
                developers {
                    developer {
                        name = "Li Ying"
                        email = "liruoer2008@yeah.net"
                        url = "https://github.com/liying2008"
                    }
                }
                scm {
                    url = "https://github.com/liying2008/cache-version-check-spring-boot"
                    connection = "scm:git:https://github.com/liying2008/cache-version-check-spring-boot.git"
                    developerConnection = "scm:git:https://github.com/liying2008/cache-version-check-spring-boot.git"
                }
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }
}

jreleaser {
    gitRootSearch = true
    signing {
        active = Active.ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                register("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}
