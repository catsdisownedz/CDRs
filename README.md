

# Call Data Records (CDR) Management System

## Overview

My Call Data Records (CDR) Management System is a comprehensive Java-based application designed to efficiently manage and analyze telecommunications data. This system integrates with PostgreSQL for robust data storage and retrieval, and it features a user-friendly interface for seamless interaction. Built with Spring Boot, this application leverages advanced Spring features such as Inversion of Control (IoC) and Dependency Injection (DI) to provide a scalable and maintainable solution.

## Features

### User Authentication and Management
- **User Registration and Login**: Allows users to create accounts and log in securely. Passwords are hashed and verified to ensure robust security.
- **User Role Management**: Easily manage different user roles and permissions.

### Call Data Records (CDR) Management
- **CDR Generation**: Generates simulated call data records including ANUM, BNUM, service type (SMS, call, data), usage, and start date-time.
- **Usage Calculation**: Computes usage statistics for calls (in minutes), data (in MB), and SMS (+1 per message).
- **Historical Data**: Allows users to access and generate CDRs based on specific dates and criteria.

### Data Persistence and Management
- **PostgreSQL Integration**: Utilizes PostgreSQL for reliable and scalable data storage. Data is automatically managed with the creation of tables and schema updates.
- **Database Schema Management**: Automatically creates and updates database tables for users and CDRs based on application requirements.

### User Interface
- **Command-Line Interface**: Provides an interactive command-line interface for user operations including login, sign-up, and navigating the main menu.
- **Visual Feedback**: Uses color-coded messages and animations to enhance user experience and provide clear feedback.

### Multi-threading and Performance
- **Efficient Data Handling**: Implements multithreading to ensure efficient database access and processing of CDR records.

### Containerization and Deployment
- **Docker Integration**: Comes with a Dockerfile to build and run the application in a containerized environment. Includes a PostgreSQL Docker image for easy setup and deployment.

## Technologies Used
- **Java**: Core programming language for application development.
- **Spring Boot**: Framework for building the application with features like IoC and DI.
- **PostgreSQL**: Relational database for storing user and CDR data.
- **Docker**: Containerization platform for deploying the application.
- **JPA/Hibernate**: ORM framework for managing database interactions.
- **Git**: Version control for managing code changes and collaboration.

--- 
### Prerequisites
1. _Make sure you have Docker downloaded._

2. _Navigate to the repository in the terminal using `cd`._

## Setup and Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/catsdisownedz/CDRs.git
   ```
2. **Build and Run the Docker Containers**:
   ```bash
   docker-compose up --build
   ```

3. **Access the Application**:
   Navigate to `http://localhost:8080` to use the application.


---
