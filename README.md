# Signup Login API

This is a RESTful API service for user signup and login functionality.

## Overview

The Signup Login API provides endpoints for user registration (signup), user authentication (login), token generation, and user information retrieval.

## Features

- User signup: Register new users with a display name, email, and password.
- User login: Authenticate users with their email and password.
- Token generation: Generate JWT tokens for authenticated users.
- User information retrieval: Get user details by providing a valid JWT token.
- Generated Custom Response
- Handled Error Globally and locally in each service method for exception

## Technologies Used

- Java
- Spring Boot (Gradle)
- Spring Security
- Hibernate/JPA
- PostgreSQL 
- JWT (JSON Web Tokens)
- Docker for database 

## Getting Started

1. Clone this repository.
2. Configure the application properties to connect to your database.
3. Build and run the application.
4. Access the API endpoints using a REST client like Postman / Inbuilt Swagger Documentation

## Development Guide 


# SignupLogin API - Developer's Guide

This guide provides instructions on setting up and running the SignupLogin API project for development purposes.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Local Setup](#local-setup)
    - [Database Configuration](#database-configuration)
    - [Build and Run](#build-and-run)
- [Docker Setup](#docker-setup)
    - [Build Docker Image](#build-docker-image)
 
- [Developer](#license)

## Prerequisites

Before you begin, ensure you have the following software installed on your system:

- Java Development Kit (JDK) 11 or later
- Gradle Build Tool
- Docker Engine

## Local Setup

### Database Configuration

1. Install PostgreSQL on your local machine if not already installed.
2. Configure the `application.properties` file located in the `src/main/resources` directory to connect to your PostgreSQL database.

### Build and Run

1. Clone this repository to your local machine:

   ```bash
   git clone <repository_url>


2. Set the appropriate details on the application.properties file
   
3. Build the application using Gradle:
    ```bash
        gradle build
4. Run the project 

# SignupLogin API - Docker Setup Guide

## Docker Setup

### Using Docker Compose

1. Clone this repository to your local machine.

2. Navigate to the project directory.

3. Build and start the Docker containers using Docker Compose.
    run this command from root of project folder
    ```bash
   docker-compose up --build -d


4. Once the containers are up and running, the SignupLogin API will be accessible at `http://localhost:8080`.



 ## Developer - Roshan Gyawali
