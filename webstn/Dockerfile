FROM openjdk:21-jdk-slim
ADD ./target/webstn-0.0.1-SNAPSHOT.jar webstn.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "webstn.jar"]