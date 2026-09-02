# main.java.Library Management System (Java)

## OVERVIEW
This is a console based main.java.Library Management System implemented in Java.  
The project simulates core library operations such as managing books, registering users, and handling book borrowing and returns through a menu-driven interface.

The system is designed with a focus on clean object-oriented structure, efficient data handling, and clear separation of responsibilities between classes.

This project highlights:
- Object-Oriented Programming (OOP) principles
- Use of Java Collections for efficient data management
- Menu-driven command-line application design
- Practical use of Git for incremental development

---

## FEATURES
- Add and display books in the library
- Prevent duplicate books using ISBN validation
- Register users with unique IDs
- Borrow and return books with availability tracking
- Menu-driven command-line interface for user interaction

---

## FILE STRUCTURE
```
LibraryManagementSystem/
│
├─ src/
│ ├─ main.java.Book.java # main.java.Book domain model
│ ├─ main.java.User.java # main.java.User domain model
│ └─ main.java.Library.java # Core application logic and menu system
│
├─ README.md # Project documentation
```

---

## HOW IT WORKS

### main.java.Book Management

- Books are stored using:
    - `ArrayList<main.java.Book>` to maintain display order
    - `HashMap<String, main.java.Book>` for fast ISBN-based lookup
- ISBN validation ensures that duplicate books cannot be added to the system.
- Each book tracks its availability status.

### main.java.User Management

- Users are registered with a user ID and name.
- A lookup method is used to retrieve users by ID before borrowing or returning books.

### Borrowing and Returning

- A user must be registered before borrowing a book.
- A book can only be borrowed if it is available.
- Returning a book updates its availability status accordingly.

### Menu System

- The application runs in a loop and presents a menu for user interaction.
- Input is handled via the command line using `Scanner`.

--- 

## HOW TO RUN

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Run `main.java.Library.java`
4. Interact with the system through the console menu

---

## SKILLS DEMONSTRATED

- Java programming fundamentals
- Object-Oriented Programming (OOP)
- Java Collections (`ArrayList`, `HashMap`)
- Input handling with `Scanner`
- Basic system design and data validation
- Git version control with incremental commits

---

## FUTURE IMPROVEMENTS

- Persist data using files or a database
- Add unit tests for core functionality
- Improve input validation and error handling
- Implement a graphical user interface (GUI)

