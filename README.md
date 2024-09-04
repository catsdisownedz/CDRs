

# Call Data Records (CDR) Management System

## Overview

The Call Data Records (CDR) Management System is a comprehensive Java-based application designed to efficiently manage and analyze telecommunications data. This system integrates with PostgreSQL for robust data storage and retrieval, featuring a user-friendly interface for seamless interaction. Built with Spring Boot, the application leverages advanced Spring features such as Inversion of Control (IoC) and Dependency Injection (DI) to provide a scalable and maintainable solution.

## Features

### User Authentication and Management
- **User Registration and Login**: Allows users to securely create accounts and log in. Passwords are hashed and verified to ensure robust security.
- **User Role Management**: Easily manage different user roles and permissions.

### Call Data Records (CDR) Management
- **CDR Generation**: Generates simulated call data records including ANUM, BNUM, service type (SMS, call, data), usage, and start date-time.
- **Usage Calculation**: Computes usage statistics for calls (in minutes), data (in MB), and SMS (+1 per message).
- **Historical Data**: Allows users to access and generate CDRs based on specific dates and criteria.

### Data Persistence and Management
- **PostgreSQL Integration**: Utilizes PostgreSQL for reliable and scalable data storage. Data is automatically managed with the creation of tables and schema updates.
- **Flyway Database Migrations**: Ensures that database schema is consistently managed across environments, automatically applying necessary migrations.
- **Hibernate/JPA**: Utilizes Hibernate for object-relational mapping (ORM), ensuring seamless interactions between Java objects and database tables.

### User Interface
- **Command-Line Interface (CLI)**: Provides an interactive command-line interface for user operations including login, sign-up, and navigating the main menu.
- **Visual Feedback**: Uses color-coded messages and animations to enhance user experience and provide clear feedback.

### Multi-threading and Performance
- **Efficient Data Handling**: Implements multithreading to ensure efficient database access and processing of CDR records.

### Containerization and Deployment
- **Docker Integration**: Comes with a Dockerfile to build and run the application in a containerized environment. Includes a PostgreSQL Docker image for easy setup and deployment.

## Technologies Used
- **Java**: Core programming language for application development.
- **Spring Boot**: Framework for building the application with features like IoC and DI.
- **PostgreSQL**: Relational database for storing user and CDR data.
- **Hibernate/JPA**: ORM framework for managing database interactions.
- **Flyway**: Database migration tool for managing schema changes.
- **Docker**: Containerization platform for deploying the application.
- **Git**: Version control for managing code changes and collaboration.

---

### Prerequisites
1. _Ensure Docker is installed on your system._
2. _Navigate to the repository in the terminal using `cd`._

## Setup and Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/catsdisownedz/CDRs.git
   ```

2. **Setup Environment Variables**:
   Create an `.env` file in the root directory with the following content (adjust as necessary):
   ```env
   POSTGRES_USER=your_postgres_user
   POSTGRES_PASSWORD=your_postgres_password
   POSTGRES_DB=your_database_name
   ```

3. **Database Migrations**:
   Flyway will automatically apply any pending migrations when the application starts.

4. **Build and Run the Docker Containers**:
   ```bash
   docker-compose up --build
   ```
   or 
    ```bash
   docker-compose up --build -d
   ```
   to run in detached mode and interact with the container, ou can access the logs later at
    ```bash
    docker-compose up --build -d
    ```

5. **Access the Application**:
   Navigate to `http://localhost:8080` to use the application.

6. **Interacting with the Database**:
   - **View CDR and User Data**: Use the command-line interface to interact with the system.
   - **Access PostgreSQL**:
     ```bash
     docker exec -it cdrs-app psql -U z -d db
     ```
     


7. **Running the Application Without Docker** (Optional):
   - Ensure PostgreSQL is running on your machine and that the database configuration in `application.yml` matches your setup.
   - Run the application using:
     ```bash
     ./mvnw spring-boot:run
     ```


### PostgreSQL Commands for Inspecting Database and Tables

Once you're inside the PostgreSQL interactive terminal (`psql`), you can use the following commands:

- **List all databases:**
  ```sql
  \l
  ```

- **Connect to a specific database:**
  ```sql
  \c <database_name>
  ```

- **List all tables in the current database:**
  ```sql
  \dt
  ```

- **Show the structure of a specific table:**
  ```sql
  \d <table_name>
  ```

- **Select all records from a specific table:**
  ```sql
  SELECT * FROM <table_name>;
  ```

These commands help you inspect the contents of the database and tables directly from the terminal.

---

## Troubleshooting

- **Database Connection Issues**: Ensure that the PostgreSQL container is running and accessible. Check the environment variables for correctness.
- **Flyway Migrations**: If migrations fail, check the `flyway_schema_history` table in your PostgreSQL database for any issues.

## Future Enhancements
- **Add a Frontend Interface**: Implement a web-based UI for easier interaction.
- **Enhanced Reporting**: Develop additional reporting features for more detailed CDR analysis.
- **Security Enhancements**: Implement OAuth or JWT-based authentication for enhanced security.

```
