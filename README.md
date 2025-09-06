# Task Manager

A Spring Boot project developed during the Software Architecture in Java postgraduate course.

## Description

This is a demo project for Spring Boot, showcasing the development of a task management application using Java.

## Architecture Diagram

![Project Diagram](docs/diagram.drawio.png)

## Features

- RESTful API for task management
- Data persistence using JPA (Java Persistence API)
- In-memory H2 database for development and testing
- Lombok for reducing boilerplate code
- Spring Boot DevTools for enhanced development experience

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd task-manager
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

## Running the Application

Run the application using Maven:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## Technologies Used

- **Spring Boot** 3.5.5
- **Java** 17
- **Spring Data JPA**
- **Spring Web**
- **H2 Database**
- **Lombok**
- **Maven**

## API Endpoints

The application provides RESTful endpoints for managing tasks. (Endpoints to be documented based on implementation.)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.
