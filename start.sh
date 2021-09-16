#!/bin/bash

echo "Starting the frontend & backend..."
trap 'kill %1; kill %2' SIGINT
(cd backend && ./mvnw spring-boot:run) & (cd client && npm run serve > /dev/null) & wait

