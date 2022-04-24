plugins {
    id("org.asciidoctor.jvm.convert")
}

ext {
    val snippetsDir = file("build/generated-snippets")
}

tasks.getByName("asciidoctor") {
    dependsOn("test")
}

tasks.getByName("jar") {
    enabled = false
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("bootRun") {
    enabled = true
}

dependencies {
    implementation(project(":web:web-service"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-restassured")
    testImplementation("io.rest-assured:spring-mock-mvc")
}