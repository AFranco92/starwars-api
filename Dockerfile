FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]