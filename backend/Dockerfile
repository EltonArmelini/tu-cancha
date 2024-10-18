FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY . /app

RUN mvn package -DskipTests

RUN mv target/*.jar app.jar

FROM openjdk:17-alpine

COPY --from=build /app/app.jar /app.jar

ENTRYPOINT ["java","-jar", "app.jar"]

EXPOSE 8080