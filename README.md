# Bank App - Digital Wallet System

## Overview

Bank App is a modern digital wallet system built with Spring Boot that provides secure financial transactions between users and merchants. The system implements a microservices architecture with external integrations for authorization and notification services.

## Purpose

This banking system serves as a digital wallet platform that enables:
- **User Wallet Management**: Create and manage digital wallets for users and merchants
- **Secure Money Transfers**: Perform validated and authorized money transfers between wallets
- **Real-time Notifications**: Send notifications for completed transactions
- **External Service Integration**: Integrate with authorization and notification services

## Technologies & Versions

### Core Technologies
- **Java**: 24 (Latest LTS)
- **Spring Boot**: 3.5.3
- **Spring Cloud**: 2025.0.0
- **Spring Data JPA**: For database operations
- **Spring Security Crypto**: For password encryption
- **Spring Validation**: For input validation
- **Spring Cloud OpenFeign**: For HTTP client communication

### Database
- **MySQL**: 8.0
- **Hibernate**: JPA implementation

### Build Tool
- **Maven**: For dependency management and build process

### Containerization
- **Docker**: Multi-stage build for optimized containerized deployment
- **Docker Compose**: Complete local development environment with MySQL database

## Architecture & Best Practices

### SOLID Principles Implementation
- **Single Responsibility**: Each service class has a single, well-defined responsibility
- **Open/Closed**: Services are open for extension but closed for modification
- **Liskov Substitution**: Proper inheritance and interface implementation
- **Interface Segregation**: Clean interfaces for different concerns
- **Dependency Inversion**: Dependency injection throughout the application

### Clean Code Practices
- **Meaningful Names**: Clear and descriptive class, method, and variable names
- **Small Functions**: Functions with single responsibilities
- **Proper Abstraction**: Well-defined layers (Controller, Service, Repository)
- **Error Handling**: Comprehensive exception handling with proper HTTP status codes
- **Validation**: Input validation using Bean Validation annotations

### Object Calisthenics
- **One Level of Indentation**: Clean, readable code structure
- **Don't Use ELSE**: Early returns and guard clauses
- **Wrap Primitives**: Using proper domain objects instead of primitives
- **First Class Collections**: Collections as first-class citizens
- **One Dot per Line**: Avoiding method chaining
- **Don't Abbreviate**: Clear, descriptive names
- **Keep Entities Small**: Focused entity classes
- **No More Than Two Instance Variables**: Clean entity design
- **No Getters/Setters/Properties**: Encapsulation through behavior

### Security Features
- **Password Encryption**: BCrypt password hashing
- **Input Validation**: Comprehensive validation using Bean Validation
- **SQL Injection Prevention**: Using JPA/Hibernate ORM
- **External Authorization**: Integration with authorization service

## System Features

### Wallet Management
- Create digital wallets for users and merchants
- Support for different wallet types (USER, MERCHANT)
- Secure password storage with BCrypt encryption
- Balance management with credit/debit operations

### Money Transfer System
- Secure money transfers between wallets
- Real-time balance validation
- External authorization service integration
- Transaction logging and tracking
- Asynchronous notification system

### External Integrations
- **Authorization Service**: Validates transfer requests
- **Notification Service**: Sends transaction notifications

## API Endpoints

### 1. Create Wallet

**Endpoint**: `POST /wallets`

**Description**: Creates a new digital wallet for users or merchants

**Request Body**:
```json
{
  "fullName": "John Doe",
  "nif": "123456789",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "walletType": "user"
}
```

**Response**:
```json
{
  "id": 1,
  "fullName": "John Doe",
  "nif": "123456789",
  "email": "john.doe@example.com",
  "balance": 0.00,
  "walletType": {
    "id": 1,
    "description": "user"
  }
}
```

**Validation Rules**:
- `fullName`: Required, not blank
- `nif`: Required, not blank, unique
- `email`: Required, not blank, unique, valid email format
- `password`: Required, not blank
- `walletType`: Required, must be "user" or "merchant"

### 2. Money Transfer

**Endpoint**: `POST /transfer`

**Description**: Performs a money transfer between two wallets

**Request Body**:
```json
{
  "value": 100.50,
  "payer": 1,
  "payee": 2
}
```

**Response**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "sender": {
    "id": 1,
    "fullName": "John Doe",
    "balance": 899.50
  },
  "receiver": {
    "id": 2,
    "fullName": "Jane Smith",
    "balance": 1100.50
  },
  "value": 100.50
}
```

**Validation Rules**:
- `value`: Required, minimum 0.01
- `payer`: Required, must exist in database
- `payee`: Required, must exist in database

**Business Rules**:
- Only USER wallet types can perform transfers
- Payer must have sufficient balance
- Transfer must be authorized by external service
- Payer and payee cannot be the same wallet

## Error Handling

The system provides comprehensive error handling with proper HTTP status codes:

### Common Error Responses

**400 Bad Request** - Validation Errors:
```json
{
  "type": "about:blank",
  "title": "Your request parameters didn't validate",
  "status": 400,
  "invalid-params": [
    {
      "name": "email",
      "reason": "must not be blank"
    }
  ]
}
```

**404 Not Found** - Wallet Not Found:
```json
{
  "type": "about:blank",
  "title": "Wallet not found",
  "status": 404,
  "detail": "Wallet with id 999 not found"
}
```

**422 Unprocessable Entity** - Business Rule Violations:
```json
{
  "type": "about:blank",
  "title": "Insufficient balance",
  "status": 422,
  "detail": "Insufficient balance for transfer"
}
```

## Getting Started

### Prerequisites
- Java 24 (for local development)
- Maven 3.6+ (for local development)
- Docker and Docker Compose (for containerized deployment)
- MySQL 8.0 (automatically provided by Docker Compose)

### Local Development Setup

#### Option 1: Using Docker Compose (Recommended)

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd bank-app
   ```

2. **Start the complete environment**:
   ```bash
   docker-compose -f docker/docker-compose.yml up -d
   ```

3. **Access the application**:
   - Application URL: `http://localhost:8080`
   - Database: `localhost:3306` (user: admin, password: 123, database: bankapp)

#### Option 2: Local Development

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd bank-app
   ```

2. **Start only the database**:
   ```bash
   docker-compose -f docker/docker-compose.yml up mysql -d
   ```

3. **Run the application locally**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**:
   - Application URL: `http://localhost:8080`
   - Database: `localhost:3306` (user: admin, password: 123, database: bankapp)

### Configuration

The application supports multiple deployment configurations:

#### Local Development (`application.properties`)
```properties
spring.application.name=bank-app
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/bankapp
spring.datasource.username=admin
spring.datasource.password=123
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

#### Docker Environment
When running with Docker Compose, the application uses environment variables:
- `SPRING_PROFILES_ACTIVE=docker`
- `SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/bankapp`
- `CLIENT_AUTHORIZATION_SERVICE_URL=https://71c496fa5e7e4d5896fff4c6805c8597.api.mockbin.io/`
- `CLIENT_NOTIFICATION_SERVICE_URL=https://c937dc8166354ea2bb59cda56cd52d00.api.mockbin.io/`

## Docker

### Dockerfile
The application uses a multi-stage Dockerfile for optimized builds:

```dockerfile
# Multi-stage Dockerfile for Spring Boot application
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

### Docker Compose
The `docker/docker-compose.yml` file provides a complete development environment:

- **MySQL 8.0**: Database with persistent volume
- **Bank App**: Spring Boot application with Docker profile
- **External Services**: Mock authorization and notification services

### Docker Commands

**Build and run the complete environment**:
```bash
docker-compose -f docker/docker-compose.yml up -d
```

**View logs**:
```bash
docker-compose -f docker/docker-compose.yml logs -f
```

**Stop the environment**:
```bash
docker-compose -f docker/docker-compose.yml down
```

**Rebuild the application**:
```bash
docker-compose -f docker/docker-compose.yml up -d --build
```

## Testing

### Running Tests
```bash
./mvnw test
```

### API Testing Examples

**Create a User Wallet**:
```bash
curl -X POST http://localhost:8080/wallets \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "nif": "123456789",
    "email": "john.doe@example.com",
    "password": "securePassword123",
    "walletType": "user"
  }'
```

**Create a Merchant Wallet**:
```bash
curl -X POST http://localhost:8080/wallets \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Tech Store",
    "nif": "987654321",
    "email": "store@techstore.com",
    "password": "merchantPass123",
    "walletType": "merchant"
  }'
```

**Perform a Transfer**:
```bash
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "value": 50.00,
    "payer": 1,
    "payee": 2
  }'
```

## Project Structure

```
src/main/java/dafon/tech/bank_app/
├── BankAppApplication.java          # Main application class
├── config/                          # Configuration classes
│   └── DataLoader.java             # Initial data loading
├── controller/                      # REST controllers
│   ├── dto/                        # Data Transfer Objects
│   ├── RestExceptionHandler.java   # Global exception handler
│   ├── TransferController.java     # Transfer endpoints
│   └── WalletController.java       # Wallet endpoints
├── entity/                         # JPA entities
│   ├── Transfer.java              # Transfer entity
│   ├── Wallet.java                # Wallet entity
│   └── WalletType.java            # Wallet type entity
├── repository/                     # Data access layer
│   ├── TransferRepository.java    # Transfer repository
│   ├── WalletRepository.java      # Wallet repository
│   └── WalletTypeRepository.java  # Wallet type repository
├── service/                        # Business logic layer
│   ├── AuthorizationService.java  # External authorization
│   ├── NotificationService.java   # External notifications
│   ├── TransferService.java       # Transfer business logic
│   └── WalletService.java         # Wallet business logic
├── client/                         # External service clients
│   ├── dto/                       # Client DTOs
│   ├── AuthorizationClient.java   # Authorization service client
│   └── NotificationClient.java    # Notification service client
└── exception/                      # Custom exceptions
    ├── BankException.java         # Base bank exception
    ├── InsufficientBalanceException.java
    ├── TransferNotAllowedForWalletTypeException.java
    ├── TransferNotAuthorizedException.java
    ├── WalletDataAlreadyExistsException.java
    └── WalletNotFoundException.java
```

## Contributing

1. Follow the established coding standards and SOLID principles
2. Write comprehensive tests for new features
3. Ensure all validations and error handling are in place
4. Update documentation for any API changes

## License

This project is licensed under the MIT License - see the LICENSE file for details.
