FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package 

EXPOSE 8080

CMD ["java", "-jar", "target/legacy-soap-1.0.jar"]