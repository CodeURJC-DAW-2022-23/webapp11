# COMPILATION CONTAINER
FROM maven:3.8.1-openjdk-17-slim AS builder

# Establish the directory where we will be executing the commands
WORKDIR /usr/src/threetechmarket

# Copy the source code, just the essential files (located in backend/app)
COPY backend/app .

# Build the application using Maven
RUN mvn clean package

# DEPLOYMENT CONTAINER
FROM openjdk:17-slim

# Limit memory usage
ENV JAVA_TOOL_OPTIONS="-Xss256K -XX:ReservedCodeCacheSize=64M -XX:MaxMetaspaceSize=100000K -Xmx64M"

# Define as the working directory the location of the JAR file
WORKDIR /usr/src/threetechmarket

# Copy the JAR file from the compilation container
COPY --from=builder /usr/src/threetechmarket/target/app-0.0.1-SNAPSHOT.jar .

# Indicate the port the application should be binded to
EXPOSE 8443

# Run the JAR file
CMD ["java", "-jar", "app-0.0.1-SNAPSHOT.jar"]
