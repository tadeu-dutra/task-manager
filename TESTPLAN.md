# Comprehensive Test Plan for Task Manager Application

## Overview
This test plan covers the entire Task Manager Spring Boot application, including all recent updates such as Users and Task Categories management, HATEOAS support, custom exception handling, PostgreSQL integration, and Docker setup. The plan is designed to be followed step-by-step, with each test case including prerequisites, steps, expected results, and actual results fields for documentation.

## Test Environment Setup
- **Application**: Task Manager (Spring Boot 3.5.5)
- **Database**: H2 (default) or PostgreSQL (via Docker)
- **Java Version**: 17+
- **Maven Version**: 3.6+
- **Testing Tools**: Postman/Curl for API testing, Browser for any web interfaces
- **Docker**: For PostgreSQL setup

### Prerequisites
1. Clone the repository and build the project: `mvn clean install`
2. Start the application: `mvn spring-boot:run` (runs on http://localhost:8080)
3. For PostgreSQL testing: Run `docker-compose up -d` and update application.properties accordingly
4. Have Postman or curl ready for API testing

## Test Categories

### 1. Task Management Tests

#### 1.1 Positive CRUD Operations for Tasks
- **Test Case 1.1.1**: Create a new task with valid data
  - Prerequisites: Valid category and user exist
  - Steps:
    1. POST /tasks with valid TaskRequest JSON
    2. Verify response status 201
    3. Verify response contains task details with HATEOAS links
  - Expected: Task created successfully with ID, description, status TO_DO, dueDate, category, user
  - Actual:

- **Test Case 1.1.2**: Retrieve all tasks
  - Steps:
    1. GET /tasks
    2. Verify response status 200
    3. Verify response is a list of TaskResponse objects with HATEOAS
  - Expected: List of all tasks
  - Actual:

- **Test Case 1.1.3**: Retrieve task by ID
  - Prerequisites: Task exists
  - Steps:
    1. GET /tasks/{id}
    2. Verify response status 200
    3. Verify response contains correct task details
  - Expected: Specific task details
  - Actual:

- **Test Case 1.1.4**: Delete task
  - Prerequisites: Task exists
  - Steps:
    1. DELETE /tasks/{id}
    2. Verify response status 200
    3. GET /tasks/{id} and verify 404
  - Expected: Task deleted successfully
  - Actual:

#### 1.2 Negative CRUD Operations for Tasks
- **Test Case 1.2.1**: Create task with invalid description (too short)
  - Steps:
    1. POST /tasks with description < 5 chars
    2. Verify 400 response with validation error
  - Expected: Validation error message
  - Actual:

- **Test Case 1.2.2**: Create task with invalid description (too long)
  - Steps:
    1. POST /tasks with description > 150 chars
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

- **Test Case 1.2.3**: Create task with past due date
  - Steps:
    1. POST /tasks with past dueDate
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

- **Test Case 1.2.4**: Retrieve non-existent task by ID
  - Steps:
    1. GET /tasks/{nonexistent_id}
    2. Verify 404 response with ErrorResponse
  - Expected: "Resource Not Found"
  - Actual:

#### 1.3 Task Filtering
- **Test Case 1.3.1**: Filter tasks by description
  - Steps:
    1. GET /tasks?description={partial_description}
    2. Verify response contains only matching tasks
  - Expected: Filtered list
  - Actual:

- **Test Case 1.3.2**: Filter tasks by username
  - Steps:
    1. GET /tasks?username={username}
    2. Verify response contains only tasks for that user
  - Expected: Filtered list
  - Actual:

#### 1.4 Task Workflow
- **Test Case 1.4.1**: Start task (TO_DO to IN_PROGRESS)
  - Prerequisites: Task in TO_DO status
  - Steps:
    1. PUT /tasks/{id}/start
    2. Verify status changed to IN_PROGRESS
  - Expected: Status updated
  - Actual:

- **Test Case 1.4.2**: Close task (IN_PROGRESS to DONE)
  - Prerequisites: Task in IN_PROGRESS
  - Steps:
    1. PUT /tasks/{id}/close
    2. Verify status changed to DONE
  - Expected: Status updated
  - Actual:

- **Test Case 1.4.3**: Cancel task
  - Prerequisites: Task in TO_DO or IN_PROGRESS
  - Steps:
    1. PUT /tasks/{id}/cancel
    2. Verify task status and behavior
  - Expected: Task cancelled
  - Actual:

### 2. User Management Tests

#### 2.1 Positive User Retrieval
- **Test Case 2.1.1**: Retrieve all users
  - Steps:
    1. GET /users
    2. Verify response status 200
    3. Verify list of UserResponse with HATEOAS
  - Expected: List of users
  - Actual:

- **Test Case 2.1.2**: Retrieve user by ID
  - Prerequisites: User exists
  - Steps:
    1. GET /users/{id}
    2. Verify correct user details
  - Expected: User details
  - Actual:

- **Test Case 2.1.3**: Filter users by name
  - Steps:
    1. GET /users?name={partial_name}
    2. Verify filtered list
  - Expected: Matching users
  - Actual:

#### 2.2 Negative User Retrieval
- **Test Case 2.2.1**: Retrieve non-existent user by ID
  - Steps:
    1. GET /users/{nonexistent_id}
    2. Verify 404 response
  - Expected: Error response
  - Actual:

### 3. Task Category Management Tests

#### 3.1 Positive CRUD for Categories
- **Test Case 3.1.1**: Create category
  - Steps:
    1. POST /categories with TaskCategoryRequest
    2. Verify 201 response with location header
  - Expected: Category created
  - Actual:

- **Test Case 3.1.2**: Retrieve all categories
  - Steps:
    1. GET /categories
    2. Verify list of categories
  - Expected: All categories
  - Actual:

- **Test Case 3.1.3**: Retrieve category by ID
  - Steps:
    1. GET /categories/{id}
    2. Verify category details
  - Expected: Category details
  - Actual:

- **Test Case 3.1.4**: Delete category
  - Steps:
    1. DELETE /categories/{id}
    2. Verify 200 response
  - Expected: Category deleted
  - Actual:

- **Test Case 3.1.5**: Filter categories by name
  - Steps:
    1. GET /categories?name={partial_name}
    2. Verify filtered list
  - Expected: Matching categories
  - Actual:

#### 3.2 Negative CRUD for Categories
- **Test Case 3.2.1**: Create category with empty name
  - Steps:
    1. POST /categories with empty name
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

- **Test Case 3.2.2**: Create category with name > 50 chars
  - Steps:
    1. POST /categories with long name
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

### 4. Validation Tests

#### 4.1 Task Validation
- **Test Case 4.1.1**: Invalid description (too short)
  - Steps:
    1. POST /tasks with description < 5 chars
    2. Verify 400 response with validation error
  - Expected: Validation error message
  - Actual:

- **Test Case 4.1.2**: Invalid description (too long)
  - Steps:
    1. POST /tasks with description > 150 chars
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

- **Test Case 4.1.3**: Invalid due date (past date)
  - Steps:
    1. POST /tasks with past dueDate
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

#### 4.2 Category Validation
- **Test Case 4.2.1**: Invalid category name (empty)
  - Steps:
    1. POST /categories with empty name
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

- **Test Case 4.2.2**: Invalid category name (too long)
  - Steps:
    1. POST /categories with name > 50 chars
    2. Verify 400 response
  - Expected: Validation error
  - Actual:

### 5. Exception Handling Tests

#### 5.1 Not Found Errors
- **Test Case 5.1.1**: Task not found
  - Steps:
    1. GET /tasks/{nonexistent_id}
    2. Verify 404 response with ErrorResponse
  - Expected: "Resource Not Found"
  - Actual:

- **Test Case 5.1.2**: User not found
  - Steps:
    1. GET /users/{nonexistent_id}
    2. Verify 404 response
  - Expected: Error response
  - Actual:

- **Test Case 5.1.3**: Category not found
  - Steps:
    1. GET /categories/{nonexistent_id}
    2. Verify 404 response
  - Expected: Error response
  - Actual:

#### 5.2 Invalid Status Transitions
- **Test Case 5.2.1**: Invalid workflow action
  - Prerequisites: Task in DONE status
  - Steps:
    1. PUT /tasks/{id}/start
    2. Verify 405 response with Problem details
  - Expected: Method Not Allowed error
  - Actual:

### 6. Internationalization Tests

#### 6.1 English Messages
- **Test Case 6.1.1**: Validation error in English
  - Steps:
    1. Set Accept-Language: en-US
    2. Trigger validation error
    3. Verify English error message
  - Expected: English message
  - Actual:

#### 6.2 Portuguese Messages
- **Test Case 6.2.1**: Validation error in Portuguese
  - Steps:
    1. Set Accept-Language: pt-BR
    2. Trigger validation error
    3. Verify Portuguese error message
  - Expected: Portuguese message
  - Actual:

### 7. HATEOAS Tests

#### 7.1 Hyperlinks in Responses
- **Test Case 7.1.1**: Task response with links
  - Steps:
    1. GET /tasks/{id}
    2. Verify response contains _links with self, collection, etc.
  - Expected: HATEOAS links present
  - Actual:

- **Test Case 7.1.2**: Collection response with links
  - Steps:
    1. GET /tasks
    2. Verify _links in response
  - Expected: Collection links
  - Actual:

### 8. Docker and Database Tests

#### 8.1 PostgreSQL Integration
- **Test Case 8.1.1**: Application with PostgreSQL
  - Prerequisites: Docker PostgreSQL running
  - Steps:
    1. Update application.properties for PostgreSQL
    2. Restart application
    3. Perform CRUD operations
    4. Verify data persists in PostgreSQL
  - Expected: Works with PostgreSQL
  - Actual:

#### 8.2 Docker Compose
- **Test Case 8.2.1**: Docker setup
  - Steps:
    1. Run docker-compose up -d
    2. Verify PostgreSQL container is running
    3. Connect to database and verify schema
  - Expected: PostgreSQL container running
  - Actual:

### 9. Unit and Integration Tests

#### 9.1 Run Maven Tests
- **Test Case 9.1.1**: Execute test suite
  - Steps:
    1. Run mvn test
    2. Verify all tests pass
    3. Check test coverage report
  - Expected: All tests pass
  - Actual:

## Test Execution Instructions

1. Start with environment setup
2. Execute tests in order (1.1, 1.2, etc.)
3. Document actual results in the "Actual:" field
4. For failed tests, note the issue and expected vs actual behavior
5. After completing all tests, summarize findings

## Summary Template
After testing:
- Total tests executed:
- Passed:
- Failed:
- Issues found:
- Recommendations:

This test plan can be shared as a prompt for collaborative testing. Please provide the actual results for each test case as you execute them.
