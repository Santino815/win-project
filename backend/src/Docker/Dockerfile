# Build-Stage
FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY . .
RUN ./gradlew build

# Runtime-Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]