FROM openjdk:21-jdk-slim
ADD ./target/chat-bot-ai-0.0.1-SNAPSHOT.jar chat_bot_ai.jar
EXPOSE 88
ENTRYPOINT ["java", "-jar", "chat_bot_ai.jar"]