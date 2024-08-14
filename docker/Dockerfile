FROM openjdk:17.0.2-slim-buster
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} dontworry.jar
ENTRYPOINT ["java", "-jar", "dontwory.jar"]