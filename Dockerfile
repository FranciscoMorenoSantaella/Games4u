FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY target/games4u.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]