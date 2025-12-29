FROM openjdk:26-ea-17-jdk-slim
WORKDIR /app
COPY target/*.jar ecommerce.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]