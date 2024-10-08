#!/bin/bash

# Navigate to the parent directory where mvnw is located
cd ../ || exit

# Stop the Spring Boot application gracefully
echo "Stopping the Spring Boot backend..."
pkill -f 'spring-boot:run'

# Stop the database service and other services using Docker
echo "Stopping the services..."
docker compose down

# Provide feedback
if [ $? -eq 0 ]; then
    echo "Backend and services stopped successfully."
else
    echo "Failed to stop the backend or services. Please check for any errors."
fi
