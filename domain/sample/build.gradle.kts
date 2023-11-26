plugins {
    kotlin("plugin.allopen")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":db:db-core"))

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor( "com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor( "jakarta.annotation:jakarta.annotation-api")
    annotationProcessor( "jakarta.persistence:jakarta.persistence-api")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
//    kapt ("jakarta.annotation:jakarta.annotation-api")
//    kapt ("jakarta.persistence:jakarta.persistence-api")
}

//configurations {
//    compileOnly {
//        extendsFrom(annotationProcessor.get())
//    }
//}
//
//val generated = "src/main/generated"


// querydsl QClass 파일 생성 위치를 지정

//tasks.withType<JavaCompile> {
//    options.getGeneratedSourceOutputDirectory().set(file(generated))
//}

// java source set 에 querydsl QClass 위치 추가
//sourceSets.main {
//    withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
//        kotlin.srcDir("$buildDir/generated/source/kapt/main")
//    }
//}
