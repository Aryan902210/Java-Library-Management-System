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

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Register User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> displayAllBooks();
                case 3 -> System.out.println("User registration coming soon");
                case 4 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 4);

        scanner.close();
    }

    public static void main(String[] args) {
        new Library().run();
    }
}

