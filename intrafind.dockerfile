FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
VOLUME /tmp
ADD target/intrafind.jar intrafind.jar
ENTRYPOINT ["java","-jar","intrafind.jar"]