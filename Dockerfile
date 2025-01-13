#Using an official Java runtime image as the base image
FROM openjdk:17-jdk-slim

#Setting the working directory inside the container
WORKDIR /app

#Copying the application's JAR file into the container
COPY target/historical-stockdata-service-0.0.1-SNAPSHOT.jar app.jar

#Exposing the port on which the app runs
EXPOSE 8081

#run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]