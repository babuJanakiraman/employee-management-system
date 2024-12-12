FROM openjdk:17-jdk-slim

ADD target/employee-service-1.0.jar employee-service.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "employee-service.jar"]