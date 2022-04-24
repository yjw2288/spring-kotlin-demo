plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":db:db-core"))
    implementation("com.querydsl:querydsl-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    sourceSets.main {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("$buildDir/generated/source/kapt/main")
        }
    }

}