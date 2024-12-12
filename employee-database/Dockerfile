FROM openjdk:17-jdk-slim

ADD target/employee-database-1.0.jar employee-database.jar

EXPOSE 8200

ENTRYPOINT ["java", "-jar", "employee-database.jar"]