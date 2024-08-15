FROM adoptopenjdk/openjdk11
COPY ./build/libs/dontworry-0.0.1-SNAPSHOT.jar dontworry.jar
RUN ls -l
RUN echo "JAR file size : $(su -sh dontworry.jar)"
ENTRYPOINT ["java", "-jar", "dontworry.jar"]
