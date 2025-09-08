# Task Manager

A Spring Boot project developed during the Software Architecture in Java postgraduate course.

## Description

This is a demo project for Spring Boot, showcasing the development of a task management application using Java.

## Architecture Diagram

<img width="500" height="381" alt="image" src="https://github.com/user-attachments/assets/92c9f751-382e-4c92-9677-b3bca6261885" />

## Features

- RESTful API for task, user, and task category management
- Data persistence using JPA (Java Persistence API)
- In-memory H2 database for development and testing
- PostgreSQL database support for production
- Docker containerization with docker-compose
- Lombok for reducing boilerplate code
- Spring Boot DevTools for enhanced development experience
- Internationalization support with message bundles
- Input validation with Bean Validation
- Model mapping with ModelMapper
- HATEOAS support for hypermedia-driven APIs
- Custom exception handling with detailed error responses

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd taskmanager
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
- **PostgreSQL**
- **Docker**
- **Lombok**
- **Bean Validation**
- **ModelMapper**
- **Spring HATEOAS**
- **Maven**

## Data Models

### Task
- `id`: Integer (auto-generated primary key)
- `description`: String (required, 5-150 characters)
- `status`: TaskStatus enum (TO_DO, IN_PROGRESS, DONE)
- `dueDate`: LocalDate (must be future or present)
- `isVisible`: Boolean
- `category`: TaskCategory (required, many-to-one relationship)
- `user`: User (required, many-to-one relationship)

### TaskStatus
Enum with values:
- TO_DO
- IN_PROGRESS
- DONE

### Other Entities
- **TaskCategory**: Represents task categories (e.g., Work, Personal)
- **User**: Represents users who own tasks
- **Role**: User roles (e.g., ADMIN, USER)

## API Endpoints

The application provides RESTful endpoints for managing tasks, users, and task categories. All endpoints return JSON responses.

### Task Endpoints

#### Retrieve All Tasks
- **GET** `/tasks`
- Description: Retrieves all tasks.
- Response: List of TaskResponse objects.

#### Retrieve Tasks by Description or Username
- **GET** `/tasks?description={description}`
- **GET** `/tasks?username={username}`
- Description: Retrieves tasks filtered by description or by username.
- Parameters: `description` or `username` (query parameter, string)
- Response: List of TaskResponse objects.

#### Retrieve Task by ID
- **GET** `/tasks/{id}`
- Description: Retrieves a specific task by its ID.
- Parameters: `id` (path parameter, integer)
- Response: TaskResponse object.
- Error: 404 if task not found.

#### Create a New Task
- **POST** `/tasks`
- Description: Creates a new task.
- Request Body: TaskRequest object (JSON)
- Response: TaskResponse object of the created task.
- Validation: Description must be 5-150 characters, due date must be future or present.

#### Delete a Task
- **DELETE** `/tasks/{id}`
- Description: Deletes a task by its ID.
- Parameters: `id` (path parameter, integer)
- Response: 200 OK if successful.
- Error: 404 if task not found.

#### Task Workflow Endpoints
- **PUT** `/tasks/{id}/start`
- Description: Moves the task status to IN_PROGRESS.
- Response: Updated TaskResponse object.

- **PUT** `/tasks/{id}/close`
- Description: Moves the task status to DONE.
- Response: Updated TaskResponse object.

- **PUT** `/tasks/{id}/cancel`
- Description: Cancels the task.
- Response: Updated TaskResponse object.

### User Endpoints

#### Retrieve All Users
- **GET** `/users`
- Description: Retrieves all users.
- Response: List of UserResponse objects.

#### Retrieve Users by Name
- **GET** `/users?name={name}`
- Description: Retrieves users filtered by name.
- Parameters: `name` (query parameter, string)
- Response: List of UserResponse objects.

#### Retrieve User by ID
- **GET** `/users/{id}`
- Description: Retrieves a specific user by ID.
- Parameters: `id` (path parameter, integer)
- Response: UserResponse object.

### Task Category Endpoints

#### Retrieve All Categories
- **GET** `/categories`
- Description: Retrieves all task categories.
- Response: List of TaskCategoryResponse objects.

#### Retrieve Categories by Name
- **GET** `/categories?name={name}`
- Description: Retrieves categories filtered by name.
- Parameters: `name` (query parameter, string)
- Response: List of TaskCategoryResponse objects.

#### Retrieve Category by ID
- **GET** `/categories/{id}`
- Description: Retrieves a specific category by ID.
- Parameters: `id` (path parameter, integer)
- Response: TaskCategoryResponse object.

#### Create a New Category
- **POST** `/categories`
- Description: Creates a new task category.
- Request Body: TaskCategoryRequest object (JSON)
- Response: TaskCategoryResponse object of the created category.

#### Delete a Category
- **DELETE** `/categories/{id}`
- Description: Deletes a category by its ID.
- Parameters: `id` (path parameter, integer)
- Response: 200 OK if successful.

### Example Request for Creating a Task
```json
{
  "description": "Complete project documentation",
  "dueDate": "2023-12-31",
  "categoryId": 1,
  "userId": 1
}
```

## Docker Setup

The project includes a `docker-compose.yml` file for setting up a PostgreSQL database.

### Running with Docker
1. Ensure Docker is installed and running.
2. From the project root directory, run:
   ```bash
   docker-compose up -d
   ```
3. The PostgreSQL database will be available on `localhost:5432`.
4. Update `application.properties` to use PostgreSQL instead of H2 if needed.

## Testing

The project includes unit and integration tests using JUnit 5 and Spring Boot Test.

### Running Tests
Run all tests with Maven:
```bash
mvn test
```

### Test Coverage
- Integration tests for service layer (e.g., TaskServiceIntegrationTest, TaskServiceTest)
- Repository tests for data access layer
- Controller tests for API endpoints

### Test Cases
The project includes comprehensive test cases covering:
- CRUD operations for tasks, users, and task categories
- Input validation (e.g., description length, due date constraints, category name constraints)
- Internationalization support (English and Portuguese messages)
- Exception handling (e.g., 404 for not found, 400 for validation errors, 405 for invalid status transitions)
- HATEOAS hyperlinks and workflow methods (start, close, cancel tasks)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License.
