# Clinicwave User Management Service

## Project Overview

The Clinicwave User Management Service is a Spring Boot application designed to manage user-related operations for the
Clinicwave platform. This service handles user registration, role assignment to users, permission assignment to roles,
and more. It solves the problem of managing user data and access control within the Clinicwave ecosystem, providing a
secure and efficient way to handle user interactions.

## Features

- User registration
- Role creation
- Permission creation
- Permission assignment to roles
- Role assignment to users
- Sending notifications to the notification service (e.g., email verification upon registration with a link and code)
- Kafka integration for event-driven notifications
- CORS configuration for frontend integration
- RESTful API endpoints

## Architecture

The service is structured as a Spring Boot application with the following major components:

- **Controllers**: Handle HTTP requests and map them to service methods.
- **Services**: Encapsulate business logic and provide a layer of abstraction between controllers and repositories.
- **Repositories**: Interface with the database using JPA/Hibernate for data persistence.
- **Configuration**: Includes setup for RestTemplate (for notifications), CORS settings, and other critical
  configurations.
- **Domain**: Contains domain models representing core entities in the system.
- **DTO**: Defines Data Transfer Objects used for API communication.
- **Enums**: Enumerations for defining various constants used throughout the application.
- **Exception**: Custom exceptions and error handling logic.
- **Initializer**: Application initialization logic and setup procedures.
- **Mapper**: Utility classes for mapping between domain models and DTOs.
- **Util**: Utility classes for common functions and operations.
- **Validator**: Custom validation logic for input data.
- **Client**: Interfaces for external service clients.
- **Audit**: Components related to auditing and logging user activities.

The service integrates with other parts of the ClinicWave system through RESTful APIs, allowing the frontend application
to interact with the backend services.

## Installation and Setup

### Prerequisites

- Docker
- Java 17 or higher
- Maven 3.6.0 or higher

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/ClinicWave/clinicwave-user-management-service.git
   cd clinicwave-user-management-service
    ```

2. Run docker-compose to start the required services:
    ```sh
    docker-compose up -d
    ```
   
3. Create `.env` in the project root and add the following environment variables:
   ```sh
   DOCKER_POSTGRES_USERNAME=<your-docker-postgres-username>
   DOCKER_POSTGRES_PASSWORD=<your-docker-postgres-password>
   ```
4. Create `secrets.properties` in the `src/main/resources` directory and add the following properties:
   ```sh
   DOCKER_POSTGRES_USERNAME=<your-docker-postgres-username>
   DOCKER_POSTGRES_PASSWORD=<your-docker-postgres-password>
   DB_HOST_EC2=<your-db-host-ec2>
   PROD_POSTGRES_USERNAME=<your-production-postgres-username>
   PROD_POSTGRES_PASSWORD=<your-production-postgres-password>
   ```

5. Build the project:
    ```sh
    mvn clean install
    ```
6. Run the application:
    ```sh
    mvn spring-boot:run
    ```
7. Testing the application:
    ```sh
    mvn test
    ```

### API Endpoints

#### User Management

- Get all users: `GET /api/users`
- Get user by ID: `GET /api/users/{id}`
- Create user: `POST /api/users`
- Update user: `PUT /api/users/{id}`
- Delete user: `DELETE /api/users/{id}`

#### Role Assignment

- Assign Role to User: `POST /api/users/{userId}/roles/{roleId}`
- Remove Role from User: `DELETE /api/users/{userId}/roles/{roleId}`

#### Verification

- Verify Email: `GET /api/verification/verify`
- Verify Email: `POST /api/verification/verify`

### Contributing

We welcome contributions to this project! If you'd like to contribute, please read our [contributing guidelines](CONTRIBUTING.md) first.

### Contact

For any questions or feedback, please feel free to reach out to the repository owner
at [aamirshaikh3232@gmail.com](aamirshaikh3232@gmail.com).

