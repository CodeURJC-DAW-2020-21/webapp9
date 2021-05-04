FROM ubuntu AS build_image 
RUN apt-get update --fix-missing
RUN apt-get install maven openjdk-11-jdk -y
WORKDIR /src
COPY pom.xml .
COPY src src
RUN mvn verify

FROM openjdk:11-jdk
COPY --from=0 ../src/target ./target
CMD cd target ; exec java -Xmx8m -Xms8m -jar words.jar