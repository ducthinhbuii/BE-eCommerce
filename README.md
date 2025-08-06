# E-Commerce Backend API

A Spring Boot-based e-commerce backend API with authentication, payment integration, and comprehensive product management features.

## 🚀 Features

- **User Management**: Registration, authentication, and profile management
- **Product Management**: View product list with filtering, sorting, and pagination capabilities
- **Shopping Cart**: Add, remove, and manage cart items
- **Order Management**: Complete order processing and tracking
- **Payment Integration**: VNPay payment gateway integration
- **Authentication**: JWT-based authentication with OAuth2 Google login
- **API Documentation**: Swagger/OpenAPI documentation
- **Security**: Spring Security with role-based access control

## 🏗️ Project Structure

```
BE-eCommerce/
├── Dockerfile                          # Docker configuration
├── ecommerce/
│   ├── pom.xml                        # Maven dependencies
│   ├── mvnw, mvnw.cmd                # Maven wrapper
│   ├── colorShop-firebase-key.json    # Firebase service account
│   └── src/main/java/com/example/ecommerce/
│       ├── EcommerceApplication.java   # Main application class
│       ├── controller/                # REST API controllers
│       │   ├── CartController.java
│       │   ├── CategoryController.java
│       │   ├── MainController.java
│       │   ├── OrderController.java
│       │   ├── PaymentController.java
│       │   ├── ProductController.java
│       │   └── UserController.java
│       ├── dto/                       # Data Transfer Objects
│       ├── model/                     # Entity models
│       ├── repository/                # Data access layer
│       ├── services/                  # Business logic layer
│       │   └── implement/            # Service implementations
│       ├── security/                  # Security configuration
│       ├── mapper/                    # Object mappers
│       ├── request/                   # Request DTOs
│       ├── response/                  # Response DTOs
│       ├── exception/                 # Custom exceptions
│       └── vnpay/                     # VNPay payment configuration
│   └── src/main/resources/
│       ├── application.properties     # Application configuration
│       └── application.yml           # OAuth2 configuration
```

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.3.0
- **Language**: Java 17
- **Database**: MongoDB
- **Security**: Spring Security + JWT + OAuth2
- **Authentication**: Google OAuth2, JWT tokens
- **Payment**: VNPay integration
- **Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Containerization**: Docker

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.8.5 or higher
- MongoDB database
- Google OAuth2 credentials
- Firebase project setup
- VNPay merchant account

## ⚙️ Configuration

### Environment Variables (.env file)

Create a `.env` file in the `ecommerce` directory with the following variables:

```env
# Google OAuth2 Configuration
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

# VNPay Configuration
VNP_TMNCODE=your_vnpay_tmn_code
VNP_SECRETKEY=your_vnpay_secret_key
```

### Application Properties (application.properties)

The application uses the following key configurations in `application.properties`:

```properties
# Application Configuration
spring.application.name=ecommerce
server.port=8081
user.timezone=Asia/Ho_Chi_Minh

# MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
spring.data.mongodb.database=ecommerce

# Security Configuration
spring.security.user.name=user
spring.security.user.password=123456

# VNPay Configuration
VNP_TMNCODE=${VNP_TMNCODE}
VNP_SECRETKEY=${VNP_SECRETKEY}
VNP_RETURN_URL=https://your-domain.com/payment-info

# Frontend URL
FE_URL=https://your-frontend-url.com/
```

## 🚀 Running the Application

### Option 1: Using Maven

```bash
# Navigate to the ecommerce directory
cd ecommerce

# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

### Option 2: Using Maven Wrapper

```bash
# Navigate to the ecommerce directory
cd ecommerce

# Run using Maven wrapper
./mvnw spring-boot:run
```

### Option 3: Using Docker

```bash
# Build the Docker image
docker build -t ecommerce-backend .

# Run the container
docker run -p 8081:8081 ecommerce-backend
```

### Option 4: Using JAR file

```bash
# Navigate to the ecommerce directory
cd ecommerce

# Build the JAR file
mvn clean package -DskipTests

# Run the JAR file
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

## 📚 API Documentation

Once the application is running, you can access the API documentation at:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs

## 🔧 Development

### Project Setup

1. Clone the repository
2. Set up MongoDB database
3. Configure environment variables
4. Configure Google OAuth2 credentials
5. Set up VNPay merchant account

### Key Components

- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic implementation
- **Repositories**: Data access layer
- **DTOs**: Data transfer objects for API communication
- **Mappers**: Object mapping between entities and DTOs
- **Security**: Authentication and authorization
- **Exception Handling**: Custom exception classes and global handlers

## 🐳 Docker Deployment

The application includes a Dockerfile for containerized deployment:

```dockerfile
FROM maven:3.8.5-openjdk-17 AS build
COPY ecommerce ./
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]
```