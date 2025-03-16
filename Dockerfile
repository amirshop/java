#Start with a base image containing Java runtime
#FROM openjdk:17-jdk-alpine

# Add the application's jar to the image
#COPY target/backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar

# execute the application
#ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]


#-----------------------------------------------------------------------

# Build stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Use a persistent Maven repo to speed up builds
VOLUME ["/root/.m2"]

# Copy pom.xml first to cache dependencies
COPY pom.xml ./

# Download dependencies before copying source code
RUN mvn dependency:resolve -B

# Copy the rest of the application
COPY . ./

# Build the application
RUN mvn clean package -DskipTests

# Debugging step: List the contents of the target folder
RUN ls -la /app/target

# Run stage
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Use `java -jar` to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080
