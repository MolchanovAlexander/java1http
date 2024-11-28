# Use an official openjdk runtime as the base image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the compiled jar file from the target directory to the container
COPY target/SERVAK-1.0.jar /app/SERVAK-1.0.jar

# Copy the resources folder (containing manifest.MF and settings.json)
COPY src/main/resources /app/resources
EXPOSE 8080
CMD ["java", "-jar", "/app/SERVAK-1.0.jar"]
