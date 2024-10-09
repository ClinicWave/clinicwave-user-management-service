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
- [Mailtrap](https://mailtrap.io) account for email testing

### Steps

Before proceeding, please read the [contributing guidelines](CONTRIBUTING.md).

1. Clone the repository:
   ```sh
   git clone https://github.com/ClinicWave/clinicwave-user-management-service.git
   cd clinicwave-user-management-service
    ```

2. Run docker-compose to start the required services:
    ```sh
    docker compose up -d
    ```

3. Build the project:
    ```sh
    ./mvnw clean install
    ```

4. Run the application:
    ```sh
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
    ```

5. Alternatively, you can use the provided `start-backend.sh` script to start the backend

6. Ensure the `start-backend.sh` script has execute permissions. If not, grant execute permissions:
    ```sh
    cd installer
    chmod +x start-backend.sh
    ```

7. Run the script to start the backend:
    ```sh
    cd installer
    ./start-backend.sh
    ```

## Stopping the service

To stop the backend service, you have multiple options:

1. Stop Through the User Interface:

    - If your application has a user interface with a stop button, you can simply click the stop button to gracefully
      terminate the backend service.

2. Using Docker Compose:

    - Navigate to the service directory and run the following command to stop the services started by Docker Compose:

        ```sh
        cd clinicwave-user-management-service
        docker compose down
        ```

    - This command stops and removes the containers created by `docker compose up -d`.

3. Using the Provided Script:

    - You can stop the backend using the provided `stop-backend.sh` script.

    - Ensure the `stop-backend.sh` script has execute permissions. If not, grant execute permissions:
        ```sh
        cd installer
        chmod +x stop-backend.sh
        ```

    - Run the script to stop the backend:

       ```sh
       cd installer
       ./stop-backend.sh
        ```

    - This script will terminate the Spring Boot application and stop any related services as defined in the script.

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

We welcome contributions to this project! If you'd like to contribute, please read
our [contributing guidelines](CONTRIBUTING.md).

### Contact

For any questions or feedback, please feel free to reach out to the repository owner
at [aamirshaikh3232@gmail.com](aamirshaikh3232@gmail.com).

