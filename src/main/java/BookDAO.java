import java.sql.*;

public class BookDAO {

    private boolean useTestDb;

    public BookDAO() {
        this.useTestDb = false;
    }

    public BookDAO(boolean useTestDb) {
        this.useTestDb = useTestDb;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection(useTestDb);
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
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

    public boolean bookExists(String isbn) {
        String sql = "SELECT isbn FROM books WHERE isbn = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error checking book: " + e.getMessage());
            return false;
        }
    }

    public Book getBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection conn = getConnection();
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

    public java.util.List<Book> getAllBooks() {
        java.util.List<Book> books = new java.util.ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = getConnection();
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

    public boolean updateAvailability(String isbn, boolean available) {
        String sql = "UPDATE books SET is_available = ? WHERE isbn = ?";
        try (Connection conn = getConnection();
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

    // Used by tests to clean up after themselves
    public void deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isbn);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}