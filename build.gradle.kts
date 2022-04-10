import org.springframework.boot.gradle.tasks.bundling.BootJar

val kotlinVersion: String by project

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.asciidoctor.jvm.convert")
	id("org.jmailen.kotlinter")

	kotlin("jvm")
	kotlin("kapt")
	kotlin("plugin.allopen")
	kotlin("plugin.spring")
	kotlin("plugin.jpa")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
	repositories {
		mavenCentral()
		maven {
			url = uri("https://repo.spring.io/milestone")
		}
	}
}

subprojects {
	apply {
		plugin("kotlin")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("kotlin-kapt")
		plugin("org.jetbrains.kotlin.plugin.allopen")
		plugin("org.jmailen.kotlinter")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	jar.enabled = true
	bootJar.enabled = false

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	val springMockkVersion: String by project

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(module = "mockito-core")
		}
		testImplementation("com.ninja-squad:springmockk:${springMockkVersion}")
	}
}