#Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Add the application's jar to the image
COPY target/backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]