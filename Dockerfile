FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/processa-video-0.0.1-SNAPSHOT.jar app.jar

ENV PORT 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]