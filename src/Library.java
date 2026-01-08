import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> bookList;        // Maintains display order
    private HashMap<String, Book> bookMap;   // Fast lookup by ISBN
    private ArrayList<User> users;
    private Scanner scanner;

    public Library() {
        bookList = new ArrayList<>();
        bookMap = new HashMap<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Add a new book with ISBN validation
    public void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        if (bookMap.containsKey(isbn)) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        Book newBook = new Book(title, author, isbn);
        bookList.add(newBook);
        bookMap.put(isbn, newBook);

        System.out.println("Book added successfully!");
    }

    // Display all books with numbering
    public void displayAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }

        System.out.println("\n--- All Books ---");
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println((i + 1) + ". " + bookList.get(i));
        }
    }

    // Register a new user
    public void registerUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        users.add(new User(userId, name));
        System.out.println("User registered successfully!");
    }

    // Find user by ID
    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // Borrow a book
    public void borrowBook() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        User user = findUserById(userId);

        if (user == null) {
            System.out.println("User not found. Please register first.");
            return;
        }

        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();

        Book book = bookMap.get(isbn);
        if (book == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is currently not available.");
            return;
        }

        book.setAvailable(false);
        System.out.println(user.getName() + " borrowed \"" + book.getTitle() + "\".");
    }

    // Return a book
    public void returnBook() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        User user = findUserById(userId);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();

        Book book = bookMap.get(isbn);
        if (book == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return;
        }

        if (book.isAvailable()) {
            System.out.println("This book was not borrowed.");
            return;
        }

        book.setAvailable(true);
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
            scanner.nextLine(); // consume newline

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

