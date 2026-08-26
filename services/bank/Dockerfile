#FROM maven:3.8.4-openjdk-17-slim AS builder
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -Dmaven.test.skip
#
#FROM tomcat:10.1.18-jdk17
#COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/

#Если локально самому собирать уже из jar файла
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker run -d --rm --name postgres -p 5432:5432 -e HOST=postgres -e POSTGRES_PASSWORD=postgres postgres:16-alpine