FROM amazoncorretto:17
WORKDIR /root
ARG JAR_FILE=build/libs/web-controller.jar
COPY ${JAR_FILE} app.jar

CMD java -server -jar app.jar