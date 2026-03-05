// Top-level build file where you can add configuration options common to all sub_projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("plugin.serialization") version "1.9.22" apply false
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}