FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./practica.jar practica.jar
ENTRYPOINT ["java","-jar","/practica.jar"]