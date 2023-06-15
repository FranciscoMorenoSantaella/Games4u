FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY target/Games4u-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]