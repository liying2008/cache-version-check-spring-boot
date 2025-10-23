/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "cache-version-check-spring-boot"
include("agreement")
include("starter")
include("demo:default-redis-template")
include("demo:custom-redis-template")
include("demo:include-module")
