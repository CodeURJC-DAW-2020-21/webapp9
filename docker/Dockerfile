FROM openjdk:11-jdk
RUN apt-get update --fix-missing
RUN apt install nodejs -y
RUN apt-get install npm -y
RUN npm install -g yarn
RUN npm install -g @angular/cli -y
COPY ./ultragamecenter/target/ultragamecenter-0.0.1-SNAPSHOT.jar .
COPY ./Frontend ./Frontend
WORKDIR ./Frontend
RUN ng build --prod --base-href=/new/
WORKDIR /Frontend/dist/angular-ugc
COPY . ./ultragamecenter/src/main/resources/static/new/
ENTRYPOINT ["java","-jar","/ultragamecenter-0.0.1-SNAPSHOT.jar"]
