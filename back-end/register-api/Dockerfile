# Start with a base image containing Java runtime (mine java 8)
FROM openjdk:11-jre-slim 
# Add Maintainer Info
# Add a volume pointing to /tmp
VOLUME /tmp
# Make port 8080 available to the world outside this container
EXPOSE 8080
# The application's jar file (when packaged)
ARG JAR_FILE
# Add the application's jar to the container
COPY ${JAR_FILE} app.jar 
# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]