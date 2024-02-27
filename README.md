# Employee Management REST API (Project for My Great Learning)

## Overview
This is a Spring Boot-based RESTful API for managing employees in an organization. It provides CRUD (Create, Read, Update, Delete) operations for employees along with additional features like role-based authentication, sorting, and searching.

## Features
1. **Role Management**: Can dynamically add roles to the system.
2. **User Authentication**: Users can be added with associated roles for authentication purposes.
3. **Employee Management**: CRUD operations for managing employee data.
4. **Role-based Access Control**: Only users with the role of ADMIN can perform employee addition operations.
5. **Secure Endpoints**: Spring Security is implemented to ensure secure access to the API.
6. **Sorting**: Employees can be sorted based on their first name in ascending or descending order.
7. **Searching**: Employees can be searched by their first name, with support for returning multiple matching records.
8. **In-memory Database**: Utilizes MySQL database for data storage fit for production use.

## Endpoints

### Roles
- `POST /api/roles`: Add a new role dynamically.
- Example Request Body:
    ```json
    {
        "name": "USER"
    }
    ```

### Users
- `POST /api/users`: Add a new user for authentication.
- Example Request Body:
    ```json
    {
        "username": "temp",
        "password": "12345",
        "roles": [{
            "id": 2,
            "name": "USER"
        }]
    }
    ```

### Employees
- `POST /api/employees`: Add a new employee (only accessible to ADMIN users).
- `GET /api/employees`: List all employees.
- `GET /api/employees/{id}`: Get details of a specific employee by ID.
- `POST /api/employees/{id}`: Update details of an existing employee (only accessible to ADMIN users).
- `DELETE /api/employees/{id}`: Delete an employee by ID (only accessible to ADMIN users).
- `GET /api/employees/search/{firstName}`: Search for employees by first name.
- `GET /api/employees/sort?order={asc/desc}`: Sort employees by first name in ascending or descending order.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MYSQL Database
- Maven (for dependency management)
- Postman (for testing)

## How to Run
1. Clone this repository.
2. Make sure you have JDK, Maven, and Postman installed on your system.
3. Open the project in your favorite IDE.
4. Replace user and pass in application.properties (can also switch profile to test mode)
5. Initatial DataLoader is provided to load sample employees, roles and users, you can clear out DataLoader to start fresh.
6. Run the application.
7. Use Postman or any other REST client to interact with the API.

## Demo
[Link to Video Demo](https://drive.google.com/file/d/1lQq1hYbp_VJoGDh81jAvbUtwr3LwwRaJ/view?usp=sharing)

## Contributors
- Anmol James Pilley
