#FROM ubuntu:latest
#LABEL authors="duyviet"
#
#ENTRYPOINT ["top", "-b"]

# Maven build container
FROM openjdk:21-jdk AS maven_build
WORKDIR /tmp/
COPY .mvn /tmp/.mvn/
COPY mvnw /tmp/
COPY eclipse-java-formatter.xml /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/
RUN ./mvnw package -Dmaven.test.skip=true

#pull base image
FROM openjdk:21-jdk
EXPOSE 80
ENV JAVA_OPTS="-Xms256m -Xmx2048m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
COPY --from=maven_build /tmp/target/*.jar /app.jar