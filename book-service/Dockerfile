# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
# Copy the pom.xml first to leverage Docker cache
COPY pom.xml .
# Copy the source code
COPY src ./src
# Package the application, skipping tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Create the final runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# Expose the port the app runs on
EXPOSE 8081
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]