FROM openjdk:21-jdk-slim
ADD ./target/open-ai-0.0.1-SNAPSHOT.jar openai-clone.jar
EXPOSE 82
ENTRYPOINT ["java", "-jar", "openai-clone.jar"]