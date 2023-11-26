plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    runtimeOnly("com.h2database:h2")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor( "com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
