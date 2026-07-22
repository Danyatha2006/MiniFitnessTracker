// Top-level build.gradle.kts

plugins {
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {

        classpath(
            "com.android.tools.build:gradle:8.4.1"
        )

        classpath(
            "com.google.gms:google-services:4.4.2"
        )
    }
}

allprojects {

    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}