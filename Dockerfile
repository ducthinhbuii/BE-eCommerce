FROM maven:3.8.5-openjdk-17 AS build
COPY ecommerce ./
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]