import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

jar.enabled = true
bootJar.enabled = false

plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    runtimeOnly("com.h2database:h2")

    implementation("com.querydsl:querydsl-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    sourceSets.main {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("$buildDir/generated/source/kapt/main")
        }
    }

}