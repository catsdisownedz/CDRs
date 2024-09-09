FROM maven:3.9.9-eclipse-temurin-22 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/CDRs-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/src/main/resources/application.yml /app/application.yml
COPY data/names.csv /app/data/names.csv
COPY data/users.csv /app/data/users.csv
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
