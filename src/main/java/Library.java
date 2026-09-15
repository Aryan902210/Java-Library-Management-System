import java.util.List;
import java.util.Scanner;

public class Library {
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private Scanner scanner;

    public Library() {
        bookDAO = new BookDAO();
        userDAO = new UserDAO();
        scanner = new Scanner(System.in);
    }

    public void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        if (bookDAO.bookExists(isbn)) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        Book newBook = new Book(title, author, isbn);
        if (bookDAO.addBook(newBook)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }

    public void displayAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n--- All Books ---");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void registerUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        User newUser = new User(userId, name);
        if (userDAO.addUser(newUser)) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
    }

    public void borrowBook() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("User not found. Please register first.");
            return;
        }

        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();
        Book book = bookDAO.getBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is currently not available.");
            return;
        }

        bookDAO.updateAvailability(isbn, false);
        System.out.println(user.getName() + " borrowed \"" + book.getTitle() + "\".");
    }

    public void returnBook() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();
        Book book = bookDAO.getBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return;
        }
        if (book.isAvailable()) {
            System.out.println("This book was not borrowed.");
            return;
        }

        bookDAO.updateAvailability(isbn, true);
        System.out.println(user.getName() + " returned \"" + book.getTitle() + "\".");
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Register User");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> displayAllBooks();
                case 3 -> registerUser();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> System.out.println("Exiting Library System. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 6);
        scanner.close();
    }

    public static void main(String[] args) {
        new Library().run();
    }
}

