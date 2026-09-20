# Library Management System (Java, MySQL, Maven)

## Overview

A console-based Library Management System built in Java, using **MySQL** for persistent data storage and **JUnit 5** for automated testing. The system simulates core library operations — managing books, registering users, and handling borrowing/returns — through a menu-driven command-line interface.

The project is structured around clean separation of concerns using the **DAO (Data Access Object) pattern**, isolating data persistence logic from application/menu logic. This design makes the storage layer swappable and testable independently of the rest of the application.

**Tech stack:** Java 23 · Maven · MySQL 8 · JDBC · JUnit 5

This project demonstrates:
- Object-Oriented Programming (OOP) principles
- Relational database design and integration via JDBC
- The DAO design pattern for separating business logic from data access
- Automated testing with JUnit 5, including test database isolation
- Maven-based dependency management and project structure
- Secure handling of credentials (kept out of version control)
- Practical, incremental use of Git for version control

---

## Features

- Add and display books in the library, backed by a MySQL database
- Prevent duplicate books using ISBN validation
- Register users with unique IDs
- Borrow and return books with real-time availability tracking, persisted across sessions
- Menu-driven command-line interface for user interaction
- Full JUnit 5 test suite covering data access logic and model behavior, run against an isolated test database

---

## Tech Stack

| Layer          | Technology              |
|----------------|--------------------------|
| Language       | Java 23                  |
| Build Tool     | Maven                    |
| Database       | MySQL 8                  |
| DB Connectivity| JDBC (`mysql-connector-j`)|
| Testing        | JUnit 5 (Jupiter)        |
| IDE            | IntelliJ IDEA            |

---

## Project Structure

```text
LibraryManagementSystem/
├── src/
│   ├── main/java/
│   │   ├── Book.java                 # Book domain model
│   │   ├── User.java                 # User domain model
│   │   ├── Library.java              # Menu logic and application entry point
│   │   ├── DatabaseConnection.java   # Manages JDBC connections (app or test DB)
│   │   ├── BookDAO.java              # Data access layer for books
│   │   └── UserDAO.java              # Data access layer for users
│   │
│   └── test/java/
│       ├── BookDAOTest.java          # Tests for BookDAO against a test database
│       ├── UserDAOTest.java          # Tests for UserDAO against a test database
│       └── BookTest.java             # Pure unit tests for Book model logic
│
├── schema.sql                        # SQL script to create both app and test databases
├── config.properties.example         # Template for local DB configuration
├── pom.xml                           # Maven build file and dependencies
└── README.md                         # This README file
```

---

## Architecture

### DAO Pattern

The application separates concerns into three layers:

- **Model classes** (`Book`, `User`) — plain data representations, with no knowledge of how or where they're stored.
- **DAO classes** (`BookDAO`, `UserDAO`) — the only classes that contain SQL and talk to the database, using `PreparedStatement` throughout to prevent SQL injection.
- **`Library`** — handles the console menu and user interaction, delegating all data operations to the DAOs.

This means the storage mechanism (currently MySQL) could be swapped out in the future without changing any menu or business logic — only the DAO implementations would need to change.

### Database Design

Two databases are used:

- **`library_db`** — the real application database.
- **`library_test_db`** — a completely separate database used only by the JUnit test suite, so tests never read, write, or interfere with real application data.

Both `BookDAO` and `UserDAO` accept an optional constructor flag to target either database, defaulting to the real one during normal application use.

**Schema:**

```sql
CREATE TABLE books (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
```

---

## How It Works

**Book Management**
- Books are persisted in the `books` table in MySQL, with ISBN as the primary key.
- ISBN validation (`bookExists`) prevents duplicate entries before insertion.
- Availability status (`is_available`) is tracked and updated in real time on borrow/return.

**User Management**
- Users are registered with a unique user ID and name, stored in the `users` table.
- Users are looked up by ID via `UserDAO` before any borrow/return action is permitted.

**Borrowing and Returning**
- A user must be registered before borrowing a book.
- A book can only be borrowed if `is_available` is `true`.
- Returning a book updates its availability back to `true`.
- All state changes are persisted immediately to MySQL — no data is lost between sessions.

**Menu System**
- The application runs in a loop, presenting a menu via the console.
- Input is handled using `Scanner`.

---

## Setup and How to Run

### Prerequisites
- Java 23 (or adjust `pom.xml` compiler settings to match your installed JDK)
- Maven (bundled with IntelliJ, or installed separately)
- MySQL Server 8.x installed and running locally

### 1. Clone the repository

git clone https://github.com/Aryan902210/Java-Library-Management-System.git

### 2. Set up the databases
Run the provided schema script in MySQL Workbench (or the MySQL CLI) to create both the application and test databases:

mysql -u root -p < schema.sql

Or open `schema.sql` in MySQL Workbench and execute it directly.

### 3. Configure your local database credentials
Copy the example config file and fill in your own MySQL credentials:

cp config.properties.example config.properties

Edit `config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/library_db
db.test.url=jdbc:mysql://localhost:3306/library_test_db
db.user=root
db.password=your_actual_password
```
> `config.properties` is excluded from version control via `.gitignore` — your credentials are never committed.

### 4. Open in IntelliJ IDEA
Open the project via `pom.xml` ("Open as Maven Project"). Maven will automatically download the required dependencies (MySQL Connector/J, JUnit 5).

### 5. Run the application
Run `Library.java` (contains the `main` method) and interact with the system through the console menu.

### 6. Run the tests
Right-click `src/test/java` → **Run All Tests**, or use Maven directly:

mvn test

---

## Testing

The project includes a JUnit 5 test suite covering:

- **`BookDAOTest`** — add, retrieve, check existence, update availability, and list-all operations against the database
- **`UserDAOTest`** — add and retrieve operations against the database
- **`BookTest`** — pure model-level logic (default availability, getters, state changes) with no database dependency

Tests targeting the database use a **separate test database** (`library_test_db`) and JUnit's `@BeforeEach`/`@AfterEach` lifecycle hooks to set up a clean DAO instance and remove test data after every test, ensuring tests are isolated and repeatable.

---

## Skills Demonstrated

- Java programming fundamentals and OOP design
- Relational database design and SQL (MySQL)
- JDBC and the DAO design pattern for data access abstraction
- Writing safe, parameterized SQL queries (`PreparedStatement`) to prevent SQL injection
- Automated testing with JUnit 5, including test isolation strategies
- Maven project structure and dependency management
- Secure credential management (excluding secrets from version control)
- Git version control with incremental, descriptive commits

---

## Future Improvements

- Add input validation and more robust error handling (e.g. malformed ISBNs, empty fields)
- Introduce a service layer between `Library` and the DAOs for additional business logic
- Add mocked unit tests (e.g. with Mockito) alongside the current database-backed tests
- Implement a GUI or REST API front end
- Add logging (e.g. via SLF4J) instead of direct console output for error handling


