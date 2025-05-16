FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app
LABEL authors = "ARWA WAIL"
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]