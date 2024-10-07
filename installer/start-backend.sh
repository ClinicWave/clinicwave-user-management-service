#!/bin/bash

# Navigate to the parent directory where mvnw is located
cd ../ || exit

# Start only the database service using Docker
echo "Starting the services..."
docker compose up -d

# Wait a few seconds to ensure the services are up
sleep 10

# Run the Spring Boot application locally
echo "Running the Spring Boot backend in docker environment..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker

# Provide feedback
if [ $? -eq 0 ]; then
    echo "Backend is running locally, and the services is running in Docker!"
    echo "You can access the API at http://localhost:8080"
else
    echo "Failed to start the backend. Please check the logs."
fi

