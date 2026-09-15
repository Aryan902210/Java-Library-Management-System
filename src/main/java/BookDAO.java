import java.sql.*;

public class BookDAO {

    // Insert a new book into the database
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    // Check if a book with this ISBN already exists
    public boolean bookExists(String isbn) {
        String sql = "SELECT isbn FROM books WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a row was found

        } catch (SQLException e) {
            System.out.println("Error checking book: " + e.getMessage());
            return false;
        }
    }

    // Get a single book by ISBN
    public Book getBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book(rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("is_available"));
                return book;
            }
            return null;

        } catch (SQLException e) {
            System.out.println("Error fetching book: " + e.getMessage());
            return null;
        }
    }

    // Get all books
    public java.util.List<Book> getAllBooks() {
        java.util.List<Book> books = new java.util.ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(rs.getString("title"), rs.getString("author"), rs.getString("isbn"));
                book.setAvailable(rs.getBoolean("is_available"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
        return books;
    }

    // Update availability status
    public boolean updateAvailability(String isbn, boolean available) {
        String sql = "UPDATE books SET is_available = ? WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, available);
            stmt.setString(2, isbn);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
            return false;
        }
    }
}