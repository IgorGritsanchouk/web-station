FROM openjdk:21-jdk-slim
ADD ./target/scanner-0.0.1-SNAPSHOT.jar scanner.jar
EXPOSE 81
ENTRYPOINT ["java", "-jar", "scanner.jar"]