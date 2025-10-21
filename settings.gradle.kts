/*
 * Copyright 2025 Li Ying.
 * Licensed under the MIT License.
 */

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "cache-version-check-spring-boot"
include("agreement")
include("starter")
include("demo:default-redis-template")
include("demo:custom-redis-template")
include("demo:include-module")
