FROM openjdk:17.0.2-slim-buster
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} dontworry.jar
RUN ls -l
RUN echo "JAR file size : $(su -sh dontworry.jar)"
ENTRYPOINT ["java", "-jar", "dontworry.jar"]
