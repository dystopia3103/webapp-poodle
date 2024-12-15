# Step 1: Use OpenJDK 21 as the base image
FROM openjdk:21-slim as builder

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and Kotlin source code
COPY pom.xml /app/
COPY src /app/src/

# Install dependencies and build the project
RUN mvn clean install -DskipTests

# Step 2: Create a minimal runtime image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime image
COPY --from=builder /app/target/poodle-0.0.1-SNAPSHOT.jar /app/poodle.jar

# Expose the port the application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/poodle.jar"]
