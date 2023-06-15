FROM adoptopenjdk:11-jre-hotspot
RUN apt-get update && apt-get install -y default-jre
WORKDIR /app
COPY target/Games4u-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]