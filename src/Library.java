import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private Scanner scanner;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
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
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("Add book feature coming soon");
                case 2 -> System.out.println("Display feature coming soon");
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
