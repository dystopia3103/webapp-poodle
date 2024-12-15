# Use an official Maven image with OpenJDK 21
FROM openjdk:8-jdk-alpine as build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .

# Copy the rest of the application code
COPY src /app/src

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as the base image for the runtime container
FROM openjdk:21-jdk-slim

# Set the working directory for the app
WORKDIR /app

# Copy the built JAR from the build container
COPY --from=build /app/target/poodle-0.0.1-SNAPSHOT.jar /app/poodle.jar

# Expose the port that the Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "poodle.jar"]
