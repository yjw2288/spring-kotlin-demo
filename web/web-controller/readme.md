./gradlew :web:web-controller:bootJar
docker build -t spring-kotlin-demo:0.1 web/web-controller
