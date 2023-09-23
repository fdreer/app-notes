FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/app-notes-0.0.1-SNAPSHOT.jar app-notes-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","app-notes-0.0.1-SNAPSHOT.jar"]