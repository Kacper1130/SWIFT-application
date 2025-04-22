FROM maven:3.9.2 AS builder
COPY pom.xml .
COPY src/ ./src/
COPY config.env .
RUN mvn clean verify -DskipITs

FROM openjdk:17-slim
COPY --from=builder /target/*.jar app.jar
COPY config.env .
COPY data.csv .
ENTRYPOINT ["java", "-jar", "app.jar"]