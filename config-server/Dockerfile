FROM openjdk:11.0.10-jre
WORKDIR /tmp
WORKDIR /tmp/setting
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]