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

- Java 17 or higher
- Maven 3.6.0 or higher

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/ClinicWave/clinicwave-user-management-service.git
   cd clinicwave-user-management-service
    ```
2. Build the project:
    ```sh
    mvn clean install
    ```
3. Run the application:
    ```sh
    mvn spring-boot:run
    ```
4. Testing the application:
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

- Verify Email: `POST /api/verification/verify`

### Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature`)
3. Make changes and commit them (`git commit -am 'Add new feature'`)
4. Push the changes to the branch (`git push origin feature`)
5. Create a pull request
6. Get your changes reviewed and merged
7. Happy coding!

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Contact

For any questions or feedback, please feel free to reach out to the repository owner at [aamirshaikh3232@gmail.com].

